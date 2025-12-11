package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.cyclic
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.maths.lcm
import com.fwdekker.std.read


// See https://adventofcode.com/2023/day/8
class Day8(sample: Int? = null) : Day(year = 2023, day = 8, sample = sample) {
    private val lines = input.linesNotBlank()

    private val steps = lines[0].toList()
    private val map = lines.drop(1)
        .map { it.split(" = ") }
        .associate { Pair(it[0], it[1].removeSurrounding("(", ")").split(", ")) }
        .mapValues { (_, it) -> mapOf('L' to it[0], 'R' to it[1]) }


    override fun part1(): Long =
        map.distance(steps, from = "AAA", to = { it == "ZZZ" })

    override fun part2(): Long =
        map.keys.filter { it.endsWith('A') }
            .map { ghost -> map.distance(steps, from = ghost, to = { it.endsWith('Z') }) }
            .lcm()


    private fun <T, R> Sequence<T>.foldUntil(initial: R, condition: (R, T) -> Boolean, operation: (R, T) -> R): R {
        var current = initial

        val iterator = iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (condition(current, next))
                break

            current = operation(current, next)
        }

        return current
    }

    private fun <N, I> Map<N, Map<I, N>>.distance(steps: List<I>, from: N, to: (N) -> Boolean): Long =
        steps.cyclic()
            .foldUntil(Pair(0L, from), { it, _ -> to(it.second) }) { (steps, location), instruction ->
                Pair(steps + 1L, this[location]!![instruction]!!)
            }
            .first
}


fun main() = Day8().run()
