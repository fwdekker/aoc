package com.fwdekker.aoc.y2025

import com.fwdekker.aoc.Day
import com.fwdekker.std.grid.Chart
import com.fwdekker.std.grid.Coords
import com.fwdekker.std.grid.allCoordsOf
import com.fwdekker.std.grid.get
import com.fwdekker.std.grid.mutable
import com.fwdekker.std.grid.principals
import com.fwdekker.std.grid.set
import com.fwdekker.std.grid.toChart
import com.fwdekker.std.read


// See https://adventofcode.com/2025/day/4
class Day4(sample: Int? = null) : Day(year = 2025, day = 4, sample = sample) {
    private val diagram = input.toChart()


    override fun part1(): Int = diagram.removable().count()

    override fun part2(): Int {
        val diagram = diagram.mutable()
        return generateSequence { diagram.removable().onEach { diagram[it] = '.' }.count() }.takeWhile { it != 0 }.sum()
    }


    private fun Chart.removable(): Sequence<Coords> =
        allCoordsOf('@').filter { coords -> principals(coords).count { this[it] == '@' } < 4 }
}


fun main() = Day4().run()
