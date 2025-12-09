package com.fwdekker.aoc.y2025

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.asPair
import com.fwdekker.std.collections.associateWithIndex
import com.fwdekker.std.collections.filterFirsts
import com.fwdekker.std.collections.firsts
import com.fwdekker.std.collections.seconds
import com.fwdekker.std.collections.swap
import com.fwdekker.std.grid.Coords
import com.fwdekker.std.grid.cardinals
import com.fwdekker.std.grid.col
import com.fwdekker.std.grid.row
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.maths.allPairs
import com.fwdekker.std.read
import com.fwdekker.std.toInts
import kotlin.math.abs


// See https://adventofcode.com/2025/day/9
class Day9(sample: Int? = null) : Day(year = 2025, day = 9, sample = sample) {
    private val tiles = read(resource).linesNotBlank().map { it.toInts(',').asPair().swap() }


    override fun part1(): Long = tiles.allPairs().maxOf { area(it) }

    override fun part2(): Long {
        val rows = (tiles.firsts().distinct() + listOf(Int.MIN_VALUE, Int.MAX_VALUE)).sorted().associateWithIndex()
        val cols = (tiles.seconds().distinct() + listOf(Int.MIN_VALUE, Int.MAX_VALUE)).sorted().associateWithIndex()
        val chart = Array(rows.size) { Array<Boolean?>(cols.size) { null } }
        val compressedTiles = tiles.map { (row, col) -> rows.getValue(row) to cols.getValue(col) }

        compressedTiles
            .let { it + it.first() }
            .windowed(2)
            .forEach { (a, b) ->
                if (a.row == b.row) line(a.col, b.col).forEach { chart[a.row][it] = true }
                else line(a.row, b.row).forEach { chart[it][a.col] = true }
            }

        var boundary = listOf(0 to 0)
        while (!boundary.isEmpty()) {
            boundary.forEach { chart[it.row][it.col] = false }
            boundary = boundary.flatMap { chart.nullNeighbors(it) }.distinct()
        }

        return compressedTiles.allPairs().zip(tiles.allPairs())
            .filterFirsts { (a, b) ->
                line(a.row, b.row).all { row ->
                    line(a.col, b.col).all { col ->
                        chart[row][col] != false
                    }
                }
            }
            .maxOf { (_, it) -> area(it) }
    }


    private fun line(from: Int, to: Int): IntRange =
        if (from < to) from..to else to..from

    private fun area(corners: Pair<Coords, Coords>): Long =
        abs(corners.first.row - corners.second.row).inc().toLong() *
            abs(corners.first.col - corners.second.col).inc().toLong()

    private fun Array<Array<Boolean?>>.nullNeighbors(coords: Coords): Collection<Coords> =
        coords.cardinals.filter { it.row in this.indices && it.col in this[0].indices && this[it.row][it.col] == null }
}


fun main() = Day9().run()
