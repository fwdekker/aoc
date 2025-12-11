package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.fold
import com.fwdekker.std.foldSelf
import com.fwdekker.std.maths.length
import com.fwdekker.std.maths.splitAtIndex
import com.fwdekker.std.sections
import com.fwdekker.std.toLongs


// See https://adventofcode.com/2024/day/11
class Day11(sample: Int? = null) : Day(year = 2024, day = 11, sample = sample) {
    private val sections = input.sections()
    private val blinks = sections[0][0].toInt()
    private val stones = sections[1][0].toLongs(' ')


    override fun part1(): Long = blink(blinks, stones).values.sum()

    override fun part2(): Long = blink(blinks * 3, stones).values.sum()


    private fun blink(times: Int, stones: List<Long>): Map<Long, Long> =
        stones.associateWith { 1L }.foldSelf(times) { currentStones ->
            currentStones.fold(mutableMapOf<Long, Long>().withDefault { 0L }) { acc, (stone, count) ->
                when {
                    stone == 0L -> listOf(1L)
                    stone.length() % 2 == 0 -> stone.splitAtIndex(stone.length() / 2).toList()
                    else -> listOf(stone * 2024L)
                }.forEach { acc[it] = acc.getValue(it) + count }

                acc
            }
        }
}


fun main() = Day11().run()
