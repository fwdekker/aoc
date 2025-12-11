package com.fwdekker.aoc.y2025

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.asPair
import com.fwdekker.std.collections.noneDistinct
import com.fwdekker.std.maths.asRangeTo
import com.fwdekker.std.maths.length
import com.fwdekker.std.maths.splitAtIndex
import com.fwdekker.std.maths.sumIf
import com.fwdekker.std.read
import com.fwdekker.std.toLongs


// See https://adventofcode.com/2025/day/2
class Day2(sample: Int? = null) : Day(year = 2025, day = 2, sample = sample) {
    private val ranges = read(resource).split(',').map { it.toLongs('-').asPair().asRangeTo() }


    override fun part1(): Long =
        ranges.flatMap { range ->
            range.filter { id -> id.length() % 2 == 0 && id.splitAtIndex(id.length() / 2).noneDistinct() }
        }.sum()

    override fun part2(): Long =
        ranges.sumOf { range ->
            range.sumIf { id ->
                val str = id.toString()
                (1..str.length / 2).any { str.length % it == 0 && str.chunked(it).noneDistinct() }
            }
        }
}


fun main() = Day3().run()
