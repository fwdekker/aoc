package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.foldSelf
import com.fwdekker.std.grid.Coords
import com.fwdekker.std.grid.cardinals
import com.fwdekker.std.grid.contains
import com.fwdekker.std.grid.coordsOf
import com.fwdekker.std.grid.get
import com.fwdekker.std.grid.getMod
import com.fwdekker.std.grid.toChart
import com.fwdekker.std.grid.width
import com.fwdekker.std.read


// See https://adventofcode.com/2023/day/21
class Day21(sample: Int? = null) : Day(year = 2023, day = 21, sample = sample) {
    private val chart = read(resource).toChart()
    private val start = setOf(chart.coordsOf('S'))


    override fun part1(): Int = flood(start, steps = 64) { it in chart && chart[it] != '#' }.size

    override fun part2(): Long {
        val targetDistance = 26501365L
        val jumps = targetDistance / chart.width
        val diffs =
            listOf(targetDistance % chart.width, chart.width, chart.width)
                .runningFold(start) { coords, steps -> flood(coords, steps) { chart.getMod(it) != '#' } }
                .zipWithNext()
                .map { (a, b) -> b.size - a.size }
        return 1L + diffs[0] + jumps * (diffs[1] + (jumps - 1) * (diffs[2] - diffs[1]) / 2)
    }


    private fun flood(start: Set<Coords>, steps: Number, filter: (Coords) -> Boolean): Set<Coords> =
        start.foldSelf(steps.toInt()) { places -> places.flatMap { it.cardinals.filter(filter) }.toSet() }
}


fun main() = Day21().run()
