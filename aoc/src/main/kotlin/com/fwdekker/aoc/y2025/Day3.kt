package com.fwdekker.aoc.y2025

import com.fwdekker.aoc.Day
import com.fwdekker.std.maths.sumOf
import com.fwdekker.std.read
import com.fwdekker.std.toDigits


// See https://adventofcode.com/2025/day/3
class Day3(sample: Int? = null) : Day(year = 2025, day = 3, sample = sample) {
    private val banks = read(resource).lines().map { it.toDigits() }


    override fun part1(): Int = banks.sumOf { bank ->
        val max1 = bank.dropLast(1).withIndex().maxBy { it.value }
        val max2 = bank.drop(max1.index + 1).max()
        max1.value * 10 + max2
    }

    override fun part2(): Long = banks.sumOf { bank ->
        (11 downTo 0).fold(bank to 0L) { (bank, joltage), selectionIdx ->
            val battery = bank.dropLast(selectionIdx).withIndex().maxBy { it.value }
            bank.drop(1 + battery.index) to joltage * 10 + battery.value
        }.second
    }
}


fun main() = Day3().run()
