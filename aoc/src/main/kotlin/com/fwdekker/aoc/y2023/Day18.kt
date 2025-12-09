package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.getMod
import com.fwdekker.std.collections.map
import com.fwdekker.std.grid.Cardinal
import com.fwdekker.std.grid.Coords
import com.fwdekker.std.grid.move
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.maths.distance
import com.fwdekker.std.maths.toLongs
import com.fwdekker.std.read
import kotlin.math.abs
import kotlin.math.roundToLong


@OptIn(ExperimentalStdlibApi::class)
// See https://adventofcode.com/2023/day/18
class Day18(sample: Int? = null) : Day(year = 2023, day = 18, sample = sample) {
    private val lines = read(resource).linesNotBlank()


    override fun part1(): Long =
        lines
            .map { line -> line.split(' ') }
            .map { Pair(Cardinal.fromChar(it[0][0], "URDL"), it[1].toInt()) }
            .runningFold(Coords(0, 0)) { previous, (direction, distance) -> previous.move(direction, distance) }
            .area()

    override fun part2(): Long =
        lines
            .map { line -> line.split(' ')[2].removeSurrounding("(#", ")") }
            .map { Pair(Cardinal.fromInt(it.drop(5).toInt()), it.take(5).hexToInt()) }
            .runningFold(Coords(0, 0)) { previous, (direction, distance) -> previous.move(direction, distance) }
            .area()


    private fun List<Coords>.area(): Long {
        val area = indices
            .map { idx -> Triple(getMod(idx - 1), getMod(idx), getMod(idx + 1)).map { it.toLongs() } }
            .sumOf { (prev, cur, next) -> cur.second * (next.first - prev.first) }
            .let { abs(it).toDouble() / 2 }
        val boundaryLength = indices
            .map { idx -> Pair(getMod(idx - 1), getMod(idx)).map { it.toLongs() } }
            .sumOf { (prev, cur) -> prev.distance(cur) }
        val interiorPoints = (area + 1 - boundaryLength / 2).roundToLong()
        return interiorPoints + boundaryLength
    }
}


fun main() = Day18().run()
