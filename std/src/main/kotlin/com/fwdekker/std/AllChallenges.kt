package com.fwdekker.std

import com.fwdekker.std.maths.sumOf
import java.io.File
import kotlin.time.Duration


/**
 * Runs all [Challenge] instances matching a particular pattern.
 *
 * @property type The general word with which to refer to [Challenge] instances.
 * @property nickname Maps a class FQN to the nickname to refer to it by.
 * @property goodTime A threshold of time such that challenges that finish below this threshold are considered "good",
 * described as a time that can be parsed by [Duration.parse].
 * @property filter Filters class FQNs of [Challenge]s to execute.
 */
class AllChallenges(
    private val type: String = "Challenge",
    private val nickname: (String) -> String = { clz -> "$type ${clz.takeLastWhile { it.isDigit() }}" },
    private val goodTime: String = "1s",
    private val filter: (String) -> Boolean,
) {
    private val loader = getClassLoader()


    fun run() = displayPerformance(runChallenges(findChallenges()))


    private fun allResources(): Sequence<File> =
        sequence {
            val roots = ArrayDeque(loader.getResources("").toList().map { url -> File(url.path) })
            while (roots.isNotEmpty()) {
                val next = roots.removeFirst()
                yield(next)
                roots.addAll(next.listFiles() ?: emptyArray())
            }
        }

    private fun findChallenges(): Sequence<NamedChallenge> =
        allResources()
            .map { it.absolutePath }
            .filter { it.endsWith(".class") }
            .map { it.dropUntilAfter("/kotlin/main/").removeSuffix(".class").replace('/', '.') }
            .filter(filter)
            .sortedWith { a, b -> a.naturalCompareTo(b) }
            .mapNotNull {
                try {
                    loader.loadClass(it).getConstructor().newInstance()
                } catch (_: ReflectiveOperationException) {
                    null
                }
            }
            .filterIsInstance<Challenge>()
            .map { NamedChallenge(nickname(it::class.java.canonicalName), it) }

    private fun runChallenges(challenges: Sequence<NamedChallenge>): Map<NamedChallenge, ChallengeResults> =
        challenges
            .associateWith { (name, challenge) ->
                challenge.timeParts()
                    .also { print("${name}. ") }
                    .onEach { (name, value, duration) -> print("$name: $value (${duration.ms} ms). ") }
                    .toList()
                    .also { print("Total time: ${it.sumOf { (_, _, duration) -> duration.ms }} ms.\n") }
            }
            .mapValues { (_, results) -> ChallengeResults(results, results.sumOf { it.duration }) }

    private fun displayPerformance(challenges: Map<NamedChallenge, ChallengeResults>) {
        val fastCount = challenges.count { (_, results) -> results.totalDuration < Duration.parse(this.goodTime) }
        val fastRatio = String.format("%.2f", fastCount.toDouble() * 100 / challenges.size)

        println(
            "\n" +
                "# Performance\n" +
                "> ${type}s solved within ${this.goodTime}:\n" +
                "$fastCount/${challenges.size} (= $fastRatio%)\n" +
                "> ${type}s in decreasing order of initialisation time:\n" +
                challenges
                    .map { (challenge, results) -> challenge.name to results.parts[0].duration }
                    .sortedByDescending { (_, time) -> time }
                    .joinToString(", ") { (name, time) -> "$name (${time.ms} ms)" } +
                "\n" +
                "> ${type}s in decreasing order of total runtime:\n" +
                challenges
                    .map { (challenge, results) -> challenge.name to results.totalDuration }
                    .sortedByDescending { (_, time) -> time }
                    .joinToString(", ") { (name, time) -> "$name (${time.ms} ms)" }
        )
    }


    private data class NamedChallenge(val name: String, val challenge: Challenge)

    private data class ChallengeResults(val parts: List<PartResult>, val totalDuration: Duration)
}
