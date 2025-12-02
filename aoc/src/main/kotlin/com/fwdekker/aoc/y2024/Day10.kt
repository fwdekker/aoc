package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.foldSelfIndexed
import com.fwdekker.std.grid.Coords
import com.fwdekker.std.grid.allCoordsOf
import com.fwdekker.std.grid.cardinals
import com.fwdekker.std.grid.getOrNull
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.read
import com.fwdekker.std.toDigits


// See https://adventofcode.com/2024/day/10
class Day10(sample: Int? = null) : Day(year = 2024, day = 10, sample = sample) {
    private val chart = read(resource).linesNotBlank().map { it.toDigits() }


    override fun part1() = trails().values.sumOf { it.toSet().size }

    override fun part2() = trails().values.sumOf { it.size }


    private fun trails(): Map<Coords, List<Coords>> =
        chart.allCoordsOf(0).associateWith { trailhead ->
            listOf(trailhead).foldSelfIndexed(9) { height, positions ->
                positions.flatMap { it.cardinals }.filter { chart.getOrNull(it) == height + 1 }
            }
        }
}


fun main() = Day10().run()
