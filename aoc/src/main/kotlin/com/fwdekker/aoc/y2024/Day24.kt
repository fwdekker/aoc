package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.maths.toLong
import com.fwdekker.std.sections


// See https://adventofcode.com/2024/day/24
class Day24(sample: Int? = null) : Day(year = 2024, day = 24, sample = sample) {
    private val sections = input.sections()
    private val circuit = Circuit(
        signals = sections[0].map { it.split(": ") }.associateTo(mutableMapOf()) { it[0] to (it[1] == "1") },
        gates = sections[1].map { it.split(' ') }.associate { it[4] to Triple(it[0], it[2], it[1]) },
    )


    override fun part1() = circuit.evaluate().fold(0L) { acc, it -> acc * 2L + it.toLong() }

    override fun part2() = circuit.findErrors().toSortedSet().joinToString(",")


    private data class Circuit(
        val signals: MutableMap<String, Boolean>,
        val gates: Map<String, Triple<String, String, String>>
    ) {
        private val maxZ = gates.keys.filter { it[0] == 'z' }.maxBy { it.drop(1).toInt() }


        fun evaluate() = gates.keys.asSequence().filter { it[0] == 'z' }.sortedDescending().map { evaluate(it) }

        private fun evaluate(out: String): Boolean =
            signals.getOrPut(out) {
                val (l, r, op) = gates[out]!!

                val lVal = evaluate(l)
                val rVal = evaluate(r)
                when (op) {
                    "AND" -> lVal and rVal
                    "OR" -> lVal or rVal
                    "XOR" -> lVal xor rVal
                    else -> error("Unknown gate operator '$op'.")
                }
            }

        fun findErrors() =
            gates
                .filter { (out, params) ->
                    val (l, r, op) = params
                    val next = gates.filterValues { (l2, r2, _) -> out == l2 || out == r2 }

                    (op != "XOR" && out[0] == 'z' && out !== maxZ) ||
                        (op == "XOR" && out[0] != 'z' && l[0] !in "xy" && r[0] !in "xy") ||
                        (op == "AND" && l != "x00" && r != "x00" && next.any { (_, it) -> it.third != "OR" }) ||
                        (op == "XOR" && next.any { (_, it) -> it.third == "OR" })
                }
                .keys
    }
}


fun main() = Day24().run()
