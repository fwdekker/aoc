package com.fwdekker.std


/**
 * Returns the (whitespace-trimmed) contents of resource [path].
 */
fun read(path: String): String =
    Thread.currentThread().contextClassLoader.getResource(path)
        ?.readText()?.trim()
        ?: error("Could not find resource $path.")
