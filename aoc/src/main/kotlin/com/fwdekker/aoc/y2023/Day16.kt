package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.grid.Chart
import com.fwdekker.std.grid.East
import com.fwdekker.std.grid.Heading
import com.fwdekker.std.grid.North
import com.fwdekker.std.grid.South
import com.fwdekker.std.grid.West
import com.fwdekker.std.grid.cols
import com.fwdekker.std.grid.contains
import com.fwdekker.std.grid.get
import com.fwdekker.std.grid.height
import com.fwdekker.std.grid.rows
import com.fwdekker.std.grid.toChart
import com.fwdekker.std.grid.width
import com.fwdekker.std.read


// See https://adventofcode.com/2023/day/16
class Day16(sample: Int? = null) : Day(year = 2023, day = 16, sample = sample) {
    private val chart = read(resource).toChart()


    override fun part1(): Int = traverse(chart, Heading(0, 0, East))

    override fun part2(): Int =
        (chart.rows.flatMap { listOf(Heading(it, 0, East), Heading(it, chart.width - 1, West)) } +
            chart.cols.flatMap { listOf(Heading(0, it, South), Heading(chart.height - 1, it, North)) })
            .maxOf { traverse(chart, it) }


    private fun Char.traverse(from: Heading): List<Heading> =
        when (this) {
            '.' -> listOf(from.go { ahead })
            '/' -> listOf(from.go { flipNE })
            '\\' -> listOf(from.go { flipNW })
            '|' -> if (from.direction.vertical)
                listOf(from.go { ahead }) else listOf(from.go { left }, from.go { right })
            '-' -> if (from.direction.horizontal)
                listOf(from.go { ahead }) else listOf(from.go { left }, from.go { right })
            else -> emptyList()
        }

    private fun traverse(map: Chart, start: Heading): Int {
        var heads = listOf(start)
        val history = heads.toMutableSet()

        while (heads.isNotEmpty()) {
            heads = heads
                .flatMap { map[it.coords].traverse(it) }
                .filter { it.coords in map && it !in history }
                .also { history.addAll(it) }
        }

        return history.distinctBy { it.coords }.size
    }
}


fun main() = Day16().run()
