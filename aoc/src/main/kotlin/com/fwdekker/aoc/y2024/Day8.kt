package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.firsts
import com.fwdekker.std.collections.map
import com.fwdekker.std.grid.allCoordsOf
import com.fwdekker.std.grid.contains
import com.fwdekker.std.grid.get
import com.fwdekker.std.grid.toChart
import com.fwdekker.std.maths.combinations
import com.fwdekker.std.maths.minus
import com.fwdekker.std.maths.naturalNumbersInt
import com.fwdekker.std.maths.plus
import com.fwdekker.std.maths.simplifyRatio


// See https://adventofcode.com/2024/day/8
class Day8(sample: Int? = null) : Day(year = 2024, day = 8, sample = sample) {
    private val chart = input.toChart()


    override fun part1() =
        antennaPairs()
            .flatMap { (a, b) -> (a - b).let { listOf(a + it, b - it) } }
            .filter { it in chart }
            .toSet().size

    override fun part2() =
        antennaPairs()
            .flatMap { (a, b) ->
                val step = (a - b).simplifyRatio()

                naturalNumbersInt()
                    .map { n -> a + step.map { it * n } }
                    .takeWhile { it in chart } +
                naturalNumbersInt()
                    .map { n -> b - step.map { it * n } }
                    .takeWhile { it in chart }
            }
            .toSet().size


    private fun antennaPairs(): Sequence<List<Pair<Int, Int>>> =
        chart.allCoordsOf { it != '.' }
            .associateWith { chart[it] }
            .toList()
            .combinations(2)
            .filter { (a, b) -> a.second == b.second }
            .map { antennas -> antennas.firsts() }
            .filter { (a, b) -> a != b }
}


fun main() = Day8().run()
