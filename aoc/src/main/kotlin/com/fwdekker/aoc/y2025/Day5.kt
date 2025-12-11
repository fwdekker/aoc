package com.fwdekker.aoc.y2025

import com.fwdekker.aoc.Day
import com.fwdekker.std.read
import com.fwdekker.std.sections
import com.fwdekker.std.toLongs


// See https://adventofcode.com/2025/day/5
class Day5(sample: Int? = null) : Day(year = 2025, day = 5, sample = sample) {
    private val sections = input.sections()
    private val ranges = sections[0].toLongs('-').map { it[0]..it[1] }
    private val ingredients = sections[1].map { it.toLong() }


    override fun part1(): Int = ingredients.count { ingredient -> ranges.any { ingredient in it } }

    override fun part2(): Long =
        ranges.sortedBy { it.first }
            .fold(0L..<0L to 0L) { (prevRange, count), nextRange ->
                if (nextRange.last <= prevRange.last)
                    prevRange to count
                else if (nextRange.first > prevRange.last)
                    nextRange to (count + prevRange.size())
                else
                    nextRange to count + (prevRange.first..<nextRange.first).size()
            }
            .let { (prevRange, count) -> count + prevRange.size() }


    private fun LongRange.size(): Long = last - first + 1
}


fun main() = Day5().run()
