package com.fwdekker.aoc.y2025

import com.fwdekker.aoc.Day
import com.fwdekker.std.allInts
import com.fwdekker.std.collections.asPair
import com.fwdekker.std.collections.map
import com.fwdekker.std.collections.mapFirst
import com.fwdekker.std.collections.mapSecond
import com.fwdekker.std.collections.product
import com.fwdekker.std.sections
import com.fwdekker.std.splitAt


// See https://adventofcode.com/2025/day/12
class Day12(sample: Int? = null) : Day(year = 2025, day = 12, sample = sample) {
    private val trees = input.sections().last().map { tree ->
        tree.splitAt(':')
            .mapFirst { it.allInts().asPair() }
            .mapSecond { it.allInts() }
    }


    override fun part1(): Int = trees.count { (size, gifts) -> size.map { it.floorDiv(3) }.product() >= gifts.sum() }

    override fun part2(): Int = 0
}


fun main() = Day12().run()
