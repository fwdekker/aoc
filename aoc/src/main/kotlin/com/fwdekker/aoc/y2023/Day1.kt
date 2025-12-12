package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.linesNotBlank


// See https://adventofcode.com/2023/day/1
class Day1(sample: Int? = null) : Day(year = 2023, day = 1, sample = sample) {
    private val lines = input.linesNotBlank()


    override fun part1(): Int =
        lines
            .map { it.filter(Char::isDigit) }
            .map { "${it.firstOrNull()}${it.lastOrNull()}" }
            .sumOf { it.toIntOrNull() ?: 0 }

    override fun part2(): Int =
        lines
            .map { it.numbers() }
            .map { "${it.firstOrNull()}${it.lastOrNull()}" }
            .sumOf { it.toIntOrNull() ?: 0 }


    private fun String.numbers(): List<Char> =
        withIndex().mapNotNull { (idx, char) ->
            if (char.isDigit()) char
            else DIGITS.entries.firstOrNull { (text, _) -> drop(idx).startsWith(text) }?.value
        }


    companion object {
        private val DIGITS =
            mapOf(
                "one" to '1', "two" to '2', "three" to '3', "four" to '4', "five" to '5', "six" to '6', "seven" to '7',
                "eight" to '8', "nine" to '9',
            )
    }
}


fun main() = Day1().run()
