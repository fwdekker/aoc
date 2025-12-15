package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.allDistinct
import com.fwdekker.std.collections.asPair
import com.fwdekker.std.collections.neverNull
import com.fwdekker.std.maths.combinations
import com.fwdekker.std.sections


// See https://adventofcode.com/2024/day/24
class Day24(sample: Int? = null, private val swapCount: Int = 4) : Day(year = 2024, day = 24, sample = sample) {
    private val sections = input.sections()
    private val circuit = Circuit(
        startSignals = sections[0].map { it.split(": ") }.associate { it[0] to (it[1] == "1") },
        gates = sections[1].map { it.split(' ') }.map { Gate(it[0], it[2], it[4], it[1]) },
    )


    override fun part1() = circuit.copy().evaluate()

    // TODO: Implement this; current implementation does not finish
    override fun part2(): String {
        return ""
        val expectedOutput = "1".repeat(circuit.wires.filter { it.startsWith('x') }.size).toLong(2)

        return circuit.gates
            .combinations(2)
            .filter { it[0] != it[1] }
            .toList()
            .combinations(swapCount)
            .filter { swaps -> swaps.flatten().allDistinct() }
            .map { swaps -> swaps.map { it.asPair() } }
            .first { swaps ->
                val newGates = circuit.gates.toMutableList()
                newGates.removeAll(swaps.flatMap { it.toList() })
                newGates.addAll(swaps.flatMap { (a, b) ->
                    listOf(
                        a.copy(wireOut = b.wireOut),
                        b.copy(wireOut = a.wireOut)
                    )
                })
                circuit.copy(gates = newGates).evaluate() == expectedOutput
            }
            .flatMap { it.toList() }
            .map { it.wireOut }
            .sorted()
            .joinToString(",")
    }


    private data class Circuit(private val startSignals: Map<String, Boolean>, val gates: Collection<Gate>) {
        private val signals = startSignals.toMutableMap()
        private val gatesMap = gates.associateBy { it.wireOut }.neverNull()
        val wires = gates.flatMap { listOf(it.wireIn1, it.wireIn2, it.wireOut) }.toSet()


        fun evaluate(): Long =
            wires
                .filter { it.startsWith('z') }
                .sortedDescending()
                .joinToString("") { if (evaluateWire(it)) "1" else "0" }
                .toLong(2)

        private fun evaluateWire(wire: String): Boolean =
            signals.getOrPut(wire) {
                val gate = gatesMap[wire]
                evaluateWire(gate.wireIn1)
                evaluateWire(gate.wireIn2)

                gate.run(signals[gate.wireIn1]!!, signals[gate.wireIn2]!!)
            }
    }

    private data class Gate(
        val wireIn1: String,
        val wireIn2: String,
        val wireOut: String,
        val type: String,
    ) {
        fun run(signalIn1: Boolean, signalIn2: Boolean): Boolean =
            when (type) {
                "AND" -> signalIn1 and signalIn2
                "OR" -> signalIn1 or signalIn2
                "XOR" -> signalIn1 xor signalIn2
                else -> error("Unknown gate type '$type'.")
            }
    }
}


fun main() = Day24().run()
