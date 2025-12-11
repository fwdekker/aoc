package com.fwdekker.aoc.y2025

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.foldSum
import com.fwdekker.std.grid.firstRow
import com.fwdekker.std.grid.toChart
import com.fwdekker.std.read


// See https://adventofcode.com/2025/day/7
class Day7(sample: Int? = null) : Day(year = 2025, day = 7, sample = sample) {
    private val diagram = input.toChart().filter { row -> row.any { it != '.' } }


    override fun part1(): Int =
        diagram
            .drop(1)
            .fold(listOf(diagram.firstRow.indexOf('S')) to 0) { (beams, splits), row ->
                beams.flatMap { col ->
                    if (row[col] == '.') listOf(col)
                    else listOf(col - 1, col + 1)
                }.let { it.distinct() to splits + it.size - beams.size }
            }
            .second

    override fun part2(): Long =
        diagram
            .drop(1)
            .fold(mapOf(diagram.firstRow.indexOf('S') to 1L)) { beams, row ->
                beams.map { (col, count) ->
                    if (row[col] == '.') mapOf(col to count)
                    else mapOf(col - 1 to count, col + 1 to count)
                }.foldSum { a, b -> (a ?: 0L) + b }
            }
            .values.sum()
}


fun main() = Day7().run()
