package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.asPair
import com.fwdekker.std.collections.firsts
import com.fwdekker.std.collections.seconds
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.maths.histogram
import com.fwdekker.std.read
import com.fwdekker.std.toInts
import kotlin.math.absoluteValue


// See https://adventofcode.com/2024/day/1
class Day1(sample: Int? = null) : Day(year = 2024, day = 1, sample = sample) {
    private val pairs = read(resource).linesNotBlank().toInts(' ').map { it.asPair() }
    private val lefts = pairs.firsts()
    private val rights = pairs.seconds()


    override fun part1(): Int =
        lefts.sorted().zip(rights.sorted()).sumOf { (a, b) -> (a - b).absoluteValue }

    override fun part2(): Int {
        val rightCounts = rights.histogram()
        return lefts.sumOf { left -> left * rightCounts.getOrDefault(left, 0) }
    }
}


fun main() = Day1().run()
