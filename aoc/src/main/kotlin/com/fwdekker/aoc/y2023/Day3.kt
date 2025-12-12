package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.grid.Chart
import com.fwdekker.std.grid.Coords
import com.fwdekker.std.grid.col
import com.fwdekker.std.grid.east
import com.fwdekker.std.grid.getOrNull
import com.fwdekker.std.grid.north
import com.fwdekker.std.grid.principals
import com.fwdekker.std.grid.rowOf
import com.fwdekker.std.grid.south
import com.fwdekker.std.grid.toChart
import com.fwdekker.std.grid.west
import com.fwdekker.std.maths.product
import kotlin.math.max


// See https://adventofcode.com/2023/day/3
class Day3(sample: Int? = null) : Day(year = 2023, day = 3, sample = sample) {
    private val chart = input.toChart()


    override fun part1(): Int =
        chart
            .flatMapIndexed { rowIndex, row ->
                row.skipFoldIndexed(emptyList<Int>()) { index, acc, _ ->
                    row.drop(index)
                        .takeWhile { it.isDigit() }
                        .let { partialRow ->
                            Pair(
                                max(1, partialRow.size),
                                if (partialRow.indices.none { chart.isNextToSymbol(rowIndex, index + it) }) acc
                                else acc + partialRow.joinToString("").toInt()
                            )
                        }
                }
            }
            .sum()

    override fun part2(): Long =
        chart
            .flatMapIndexed { rowIndex, row ->
                row.asSequence()
                    .withIndex()
                    .filter { (_, char) -> char == '*' }
                    .map { (index, _) -> chart.getSurroundingNumbers(Coords(rowIndex, index)) }
                    .filter { it.size == 2 }
                    .map { it.product() }
            }
            .sum()


    private fun <T, R> List<T>.skipFoldIndexed(init: R, action: (Int, R, T) -> Pair<Int, R>): R {
        // Like `foldIndexed` except you can also return an `Int` saying how many subsequent iterations to skip

        val lastIndex = this.lastIndex

        var index = 0
        var accumulator = init
        while (index < lastIndex) {
            val out = action(index, accumulator, this[index])
            index += out.first
            accumulator = out.second
        }

        return accumulator
    }

    private fun Chart.isNextToSymbol(rowIdx: Int, colIdx: Int): Boolean =
        Coords(rowIdx, colIdx).principals.asSequence().mapNotNull(this::getOrNull).any { it != '.' && !it.isDigit() }

    private fun Chart.getNumber(coords: Coords): Int? {
        if (getOrNull(coords)?.isDigit() == false) return null

        val row = rowOf(coords)
        return row.reversed().drop(row.size - 1 - coords.col).takeWhile { it.isDigit() }.drop(1).reversed()
            .plus(row.drop(coords.col).takeWhile { it.isDigit() })
            .joinToString("").toInt()
    }

    private fun Chart.getSurroundingNumbers(coords: Coords): List<Int> =
        listOf(coords.north(), coords, coords.south())
            .flatMap { other ->
                val char = getOrNull(other)

                if (char == null) emptyList()
                else if (char.isDigit()) listOf(getNumber(other))
                else listOf(getNumber(other.west()), getNumber(other.east()))
            }
            .filterNotNull()
}


fun main() = Day3().run()
