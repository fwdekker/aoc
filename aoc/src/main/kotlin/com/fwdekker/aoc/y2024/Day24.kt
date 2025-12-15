package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.allDistinct
import com.fwdekker.std.collections.associateWithIndex
import com.fwdekker.std.maths.combinations
import com.fwdekker.std.maths.toLong
import com.fwdekker.std.sections
import java.util.SortedSet


// See https://adventofcode.com/2024/day/24
class Day24(
    sample: Int? = null,
    private val swapCount: Int = 4,
    private val operator: (String, String) -> String =
        { a, b -> (a.bitStringToLong() + b.bitStringToLong()).toBitString() },
) : Day(year = 2024, day = 24, sample = sample) {
    private val sections = input.sections()
    private val circuit = Circuit(
        startSignals = sections[0].map { it.split(": ") }.associate { it[0] to (it[1] == "1") },
        gates = sections[1].map { it.split(' ') }.map { Gate.from(it[0], it[2], it[4], it[1]) },
    )


    override fun part1(): Long = circuit.evaluate().bitStringToLong()

    // TODO: Implement this; current implementation does not finish
    override fun part2(): String {
        val inputX = circuit.wiresX.toBitString { circuit.getSignal(it)!! }
        val inputY = circuit.wiresY.toBitString { circuit.getSignal(it)!! }
        val expectZ = operator(inputX, inputY)

        return circuit.findErrors(operator)
            .also { println("Found ${it.size} candidates: $it") }
            .combinations(2)
            .filter { (a, b) -> a != b }
            .toList()
            .also { println("Found ${it.size} pairs: $it") }
            .combinations(swapCount)
            .filter { swaps -> swaps.flatten().allDistinct() }
            .distinct()
            .first { swaps -> circuit.swap(swaps).evaluate() == expectZ }
            .flatten()
            .sorted()
            .joinToString(",")
    }


    private data class Circuit(val startSignals: Map<String, Boolean>, val gates: List<Gate>) {
        val wires: Set<String> = gates.flatMapTo(mutableSetOf()) { it.wires }
        val wiresX: SortedSet<String> by lazy { wires.filter { it.startsWith('x') }.toDescendingSortedSet() }
        val wiresY: SortedSet<String> by lazy { wires.filter { it.startsWith('y') }.toDescendingSortedSet() }
        val wiresZ: SortedSet<String> by lazy { wires.filter { it.startsWith('z') }.toDescendingSortedSet() }

        private val signals: MutableMap<String, Boolean> = startSignals.toMutableMap()
        private val gatesByWireOut: Map<String, Gate> = gates.associateBy { it.wireOut }
        private val gatesToIndex: Map<String, Int> = gates.map { it.wireOut }.associateWithIndex()


        init {
            require(wiresX.size == wiresY.size)
        }


        fun getGate(wireOut: String): Gate? = gatesByWireOut[wireOut]

        fun getSignal(wire: String): Boolean? = signals[wire]

        fun evaluate(): String = wiresZ.toBitString { evaluateWire(it) }

        fun evaluateWire(wire: String): Boolean =
            signals.getOrPut(wire) {
                val gate = gatesByWireOut[wire]!!
                evaluateWire(gate.wireIn1)
                evaluateWire(gate.wireIn2)

                gate.run(signals[gate.wireIn1]!!, signals[gate.wireIn2]!!)
            }

        fun findErrors(operator: (String, String) -> String): Set<String> {
            val inputSize = wiresX.size
            return listOf(
                "0".repeat(inputSize),
                "1".repeat(inputSize),
                "01".repeat(inputSize / 2).padEnd(inputSize, '0'),
                "10".repeat(inputSize / 2).padEnd(inputSize, '1'),
            ).combinations(2).flatMap { findErrors(operator, it[0], it[1]) }.toSet()
        }

        private fun findErrors(operator: (String, String) -> String, x: String, y: String): Set<String> {
            val actualZ = copy(startSignals = x.bitStringToSignalMap("x") + y.bitStringToSignalMap("y")).evaluate()
            val expectZ = operator(x, y).padStart(wiresZ.size, '0')

            return expand(wiresZ.withIndex().filter { (idx, _) -> expectZ[idx] != actualZ[idx] }.map { (_, it) -> it })
        }

        private fun expand(wiresOut: Iterable<String>): Set<String> {
            val expandedWiresOut = wiresOut.toMutableSet()
            var border: Collection<String> = expandedWiresOut
            while (true) {
                border = border
                    .flatMap { getGate(it)!!.wiresIn }
                    .filter { !it.startsWith('x') && !it.startsWith('y') }
                    .ifEmpty { break }
                expandedWiresOut += border
            }
            return expandedWiresOut
        }

        fun swap(swaps: List<List<String>>): Circuit {
            val newGates = gates.toMutableList()
            swaps.forEach { (from, to) ->
                val fromIdx = gatesToIndex[from]!!
                val toIdx = gatesToIndex[to]!!
                val fromGate = newGates[fromIdx]
                val toGate = newGates[toIdx]
                newGates[fromIdx] = fromGate.copy(wireOut = toGate.wireOut)
                newGates[toIdx] = toGate.copy(wireOut = fromGate.wireOut)
            }
            return copy(gates = newGates)
        }
    }


    private interface Gate {
        val type: String
        val wireIn1: String
        val wireIn2: String
        val wireOut: String
        val wiresIn: List<String> get() = listOf(wireIn1, wireIn2)
        val wires: List<String> get() = listOf(wireIn1, wireIn2, wireOut)


        fun copy(
            wireIn1: String = this.wireIn1,
            wireIn2: String = this.wireIn2,
            wireOut: String = this.wireOut,
        ): Gate = from(wireIn1, wireIn2, wireOut, this.type)

        fun run(signalIn1: Boolean, signalIn2: Boolean): Boolean


        companion object {
            fun from(wireIn1: String, wireIn2: String, wireOut: String, type: String) =
                when (type) {
                    "AND" -> AndGate(Triple(wireIn1, wireIn2, wireOut))
                    "OR" -> OrGate(Triple(wireIn1, wireIn2, wireOut))
                    "XOR" -> XorGate(Triple(wireIn1, wireIn2, wireOut))
                    else -> error("Unknown gate type '$type'.")
                }
        }
    }

    @JvmInline
    private value class AndGate(private val descriptor: Triple<String, String, String>) : Gate {
        override val type: String get() = "AND"
        override val wireIn1: String get() = descriptor.first
        override val wireIn2: String get() = descriptor.second
        override val wireOut: String get() = descriptor.third

        override fun run(signalIn1: Boolean, signalIn2: Boolean): Boolean = signalIn1 && signalIn2
    }

    @JvmInline
    private value class OrGate(private val descriptor: Triple<String, String, String>) : Gate {
        override val type: String get() = "OR"
        override val wireIn1: String get() = descriptor.first
        override val wireIn2: String get() = descriptor.second
        override val wireOut: String get() = descriptor.third

        override fun run(signalIn1: Boolean, signalIn2: Boolean): Boolean = signalIn1 || signalIn2
    }

    @JvmInline
    private value class XorGate(private val descriptor: Triple<String, String, String>) : Gate {
        override val type: String get() = "XOR"
        override val wireIn1: String get() = descriptor.first
        override val wireIn2: String get() = descriptor.second
        override val wireOut: String get() = descriptor.third

        override fun run(signalIn1: Boolean, signalIn2: Boolean): Boolean = signalIn1 != signalIn2
    }
}

fun <T> Iterable<T>.toBitString(map: (T) -> Boolean): String = joinToString("") { if (map(it)) "1" else "0" }

fun Long.toBitString(): String = toString(2)

fun String.bitStringToLong(): Long = fold(0L) { acc, char -> acc * 2L + (char == '1').toLong() }

fun String.bitStringToSignalMap(prefix: String): Map<String, Boolean> =
    reversed().mapIndexed { idx, value -> "$prefix${idx.toString().padStart(2, '0')}" to (value == '1') }.toMap()

fun <T : Comparable<T>> Iterable<T>.toDescendingSortedSet(): SortedSet<T> = toSortedSet { a, b -> -a.compareTo(b) }


fun main() = Day24().run()
