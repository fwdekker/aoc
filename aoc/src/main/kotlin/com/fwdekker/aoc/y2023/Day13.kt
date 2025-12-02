package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.grid.Chart
import com.fwdekker.std.grid.toCharts
import com.fwdekker.std.grid.transpose
import com.fwdekker.std.read


// See https://adventofcode.com/2023/day/13
class Day13(sample: Int? = null) : Day(year = 2023, day = 13, sample = sample) {
    private val patterns = read(resource).toCharts()


    override fun part1(): Int = patterns.sumOf { it.value(targetDiff = 0) }

    override fun part2(): Int = patterns.sumOf { it.value(targetDiff = 1) }


    private fun Chart.diff(a: Int, b: Int): Int =
        if (a !in indices || b !in indices) 0
        else (this[a] zip this[b]).count { it.first != it.second }

    private fun Chart.mirrors(targetDiff: Int = 0): List<Int> =
        indices.filter { row -> indices.sumOf { offset -> diff(row - offset - 1, row + offset) } == targetDiff }

    private fun Chart.value(targetDiff: Int = 0): Int =
        mirrors(targetDiff).sumOf { it * 100 } + transpose().mirrors(targetDiff).sum()
}


fun main() = Day13().run()
