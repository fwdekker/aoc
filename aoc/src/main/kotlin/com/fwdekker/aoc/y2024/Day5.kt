package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.asPair
import com.fwdekker.std.read
import com.fwdekker.std.sections
import com.fwdekker.std.toInts


// See https://adventofcode.com/2024/day/5
class Day5(sample: Int? = null) : Day(year = 2024, day = 5, sample = sample) {
    private val sections = input.sections()
    private val rules =
        sections[0]
            .map { rule -> rule.toInts('|').asPair() }
            .groupBy({ it.first }, { it.second })
            .mapValues { (_, it) -> it.toSet() }
    private val manuals = sections[1].map { it.toInts(',') }

    private val comparator = { a: Int, b: Int ->
        when {
            b in rules.getOrDefault(a, emptySet()) -> -1
            a in rules.getOrDefault(b, emptySet()) -> 1
            else -> 0
        }
    }


    override fun part1() =
        manuals.filter { it == it.sortedWith(comparator) }.sumOf { it[it.size / 2] }

    override fun part2() =
        manuals.filter { it != it.sortedWith(comparator) }.map { it.sortedWith(comparator) }.sumOf { it[it.size / 2] }
}


fun main() = Day5().run()
