package com.fwdekker.aoc.y2025

import com.fwdekker.aoc.Day
import com.fwdekker.std.allLongs
import com.fwdekker.std.collections.mapLast
import com.fwdekker.std.grid.padRows
import com.fwdekker.std.grid.transpose
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.maths.product
import com.fwdekker.std.maths.sumOfIndexed
import com.fwdekker.std.read


// See https://adventofcode.com/2025/day/6
class Day6(sample: Int? = null) : Day(year = 2025, day = 6, sample = sample) {
    private val text = read(resource).linesNotBlank()


    override fun part1(): Long {
        val numbers = text.dropLast(1).allLongs().transpose()
        val operators = text.last().filter { it != ' ' }

        return numbers.sumOfIndexed { idx, col ->
            if (operators[idx] == '+') col.sum()
            else col.product()
        }
    }

    override fun part2(): Long =
        text
            .map { it.toList() }
            .padRows(' ')
            .transpose()
            .fold(listOf<Pair<Char, Long>>()) { problems, col ->
                val operator = col.last()
                val number = col.dropLast(1).filter { it != ' ' }.joinToString(separator = "").toLongOrNull()

                if (operator != ' ') {
                    problems + (operator to (number ?: (if (operator == '+') 0L else 1L)))
                } else if (number == null) {
                    problems
                } else {
                    problems.mapLast { (operator, total) ->
                        operator to if (operator == '+') total + number else total * number
                    }
                }
            }
            .sumOf { it.second }
}


fun main() = Day6().run()
