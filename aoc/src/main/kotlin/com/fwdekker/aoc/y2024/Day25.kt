package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.seconds
import com.fwdekker.std.grid.Chart
import com.fwdekker.std.grid.col
import com.fwdekker.std.grid.cols
import com.fwdekker.std.grid.firstRow
import com.fwdekker.std.sections


// See https://adventofcode.com/2024/day/25
class Day25(sample: Int? = null) : Day(year = 2024, day = 25, sample = sample) {
    private val sections = input.sections()
    private val height = sections.first().size

    private val products = sections
        .map { product -> product.map { it.toList() } }
        .map { product -> product.firstRow.all { it == '#' } to product.toHeights() }
    private val locks = products.filter { it.first }.seconds()
    private val keys = products.filter { !it.first }.seconds()


    override fun part1() =
        locks.sumOf { lock -> keys.count { key -> lock.zip(key) { a, b -> a + b }.all { it <= height } } }

    override fun part2() = 0


    private fun Chart.toHeights(): List<Int> = cols.map { col -> col(col).count { it == '#' } }
}


fun main() = Day25().run()
