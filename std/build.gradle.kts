plugins {
    id("challenges.common")
}


tasks {
    register("buildTemplates") {
        group = "build"

        val targets = layout.projectDirectory.asFileTree.asSequence()
        val comment = "///"
        val prefix = "$comment Kotlin template. DO NOT EDIT DIRECTLY."
        val directiveKinds = mapOf(
            "Base" to { generatedFile: String, templateFile: String ->
                logger.debug("Loading template from '{}' into '{}'.", templateFile, generatedFile)
                File(generatedFile).parentFile.resolve(templateFile).readText()
            },
            "Replace" to { template: String, args: String ->
                val (search, replace) = args.split("=>")
                template.replace(Regex(search), replace)
            },
            "Import" to { template: String, clz: String ->
                val lines = template.lines()
                val preamble = lines.takeWhile { !it.startsWith("import ") }
                val imports = lines.drop(preamble.size).takeWhile { it.startsWith("import ") }
                val remainder = lines.drop(preamble.size + imports.size)

                val newLines = preamble + (imports + "import $clz").sorted() + remainder
                newLines.joinToString("\n")
            }
        )

        doLast {
            for (file in targets) {
                if (!file.name.endsWith(".kt")) continue
                val header = file.readLines().takeWhile { it.startsWith(comment) }
                if (header.isEmpty() || !header.first().startsWith(prefix)) continue

                logger.debug("Processing template at '{}'.", file)

                val output = header.drop(1)
                    .map { line -> line.removePrefix("$comment ").split(": ").let { it[0] to it[1] } }
                    .fold(file.absolutePath) { template, (directive, args) ->
                        directiveKinds.getValue(directive).invoke(template, args)
                    }

                file.writeText("${header.joinToString("\n")}\n$output")
            }
        }
    }

    build {
        dependsOn("buildTemplates")
    }
}
