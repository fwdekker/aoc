package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.grid.Coords
import com.fwdekker.std.grid.allCoordsOf
import com.fwdekker.std.grid.col
import com.fwdekker.std.grid.cols
import com.fwdekker.std.grid.row
import com.fwdekker.std.grid.rows
import com.fwdekker.std.grid.toChart
import com.fwdekker.std.maths.cartesian
import com.fwdekker.std.maths.toLongs
import kotlin.math.max
import kotlin.math.min


// See https://adventofcode.com/2023/day/11
class Day11(sample: Int? = null) : Day(year = 2023, day = 11, sample = sample) {
    private val chart = input.toChart()

    private val expandX = chart.rows.filter { x -> chart[x].all { it == '.' } }.toLongs()
    private val expandY = chart.cols.filter { y -> chart.all { it[y] == '.' } }.toLongs()
    private val galaxies = chart.allCoordsOf('#').toList()
    private val pairs = galaxies.cartesian(galaxies)


    override fun part1(): Long = pairs.sumOf { (a, b) -> a.distance(b, expandX, expandY, 2L) } / 2

    override fun part2(): Long = pairs.sumOf { (a, b) -> a.distance(b, expandX, expandY, 1000000L) } / 2


    private fun Int.distance(that: Int, expanded: List<Long>, weight: Long): Long =
        (min(this, that)..<max(this, that)).toLongs().sumOf { if (it in expanded) weight else 1L }

    private fun Coords.distance(that: Coords, expandX: List<Long>, expandY: List<Long>, weight: Long): Long =
        this.row.distance(that.row, expandX, weight) + this.col.distance(that.col, expandY, weight)
}


fun main() = Day11().run()
