package com.fwdekker.aoc.y2025

import com.fwdekker.aoc.Day
import com.fwdekker.std.allWords
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.maths.Graph
import com.fwdekker.std.maths.sumOf
import com.fwdekker.std.maths.toLong


// See https://adventofcode.com/2025/day/11
class Day11(sample: Int? = null) : Day(year = 2025, day = 11, sample = sample) {
    private val graph = input.linesNotBlank()
        .allWords()
        .associate { it[0].dropLast(1) to it.drop(1) }
        .let { description ->
            object : Graph<String>() {
                override val nodes: Collection<String> = description.keys

                override fun getNeighbours(node: String): Collection<String> = description[node] ?: emptyList()

                override fun getWeight(start: String, end: String): Long = 1L
            }
        }


    override fun part1(): Int = graph.allPaths("you", "out").count()

    override fun part2(): Long = graph.countPaths("svr", setOf("fft", "dac"), "out")


    // Assumes that there are no cycles!
    private fun Graph<String>.countPaths(start: String, targets: Set<String>, end: String): Long {
        val memory = mutableMapOf<Pair<String, Set<String>>, Long>()

        fun step(current: String, targets: Set<String>): Long =
            memory.getOrPut(current to targets) {
                getNeighbours(current).sumOf {
                    if (it == end) (targets.isEmpty()).toLong()
                    else step(it, if (it in targets) targets.minus(it) else targets)
                }
            }

        return step(start, targets)
    }
}


fun main() = Day11().run()
