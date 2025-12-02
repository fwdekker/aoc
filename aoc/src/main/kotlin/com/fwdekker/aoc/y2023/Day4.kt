package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.appendOnEach
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.read
import com.fwdekker.std.toInts
import kotlin.math.pow


// See https://adventofcode.com/2023/day/4
class Day4(sample: Int? = null) : Day(year = 2023, day = 4, sample = sample) {
    private val lines = read(resource).linesNotBlank()
    private val winsByLine =
        lines
            .map { line -> line.substringAfter(": ").trim().split('|').map { it.toInts(Regex("\\s+")).toSet() } }
            .map { (drawn, winning) -> drawn.intersect(winning).size }


    override fun part1(): Int = winsByLine.sumOf { 2.0.pow(it - 1).toInt() }

    override fun part2(): Int =
        lines.indices.toMutableList()
            .appendOnEach { cards, it -> cards.addAll((it + 1)..(it + winsByLine[it])) }
            .size
}


fun main() = Day4().run()
