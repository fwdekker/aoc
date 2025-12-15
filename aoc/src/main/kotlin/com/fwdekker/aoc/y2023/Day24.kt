package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.maths.Ray2D
import com.fwdekker.std.maths.cartesian
import com.fwdekker.std.maths.vector.rref
import com.fwdekker.std.maths.vector.toBigDecimalMatrix
import com.fwdekker.std.maths.vector.toVector
import com.fwdekker.std.toLongs
import kotlin.math.roundToLong


// See https://adventofcode.com/2023/day/24
class Day24(
    sample: Int? = null,
    private val coordinateRange: LongRange = 200000000000000L..400000000000000L,
) : Day(year = 2023, day = 24, sample = sample) {
    private val stones = input.linesNotBlank().map { line -> line.split('@').map { it.toLongs(',').toVector() } }


    override fun part1(): Int =
        stones
            .map { Ray2D(it[0][0..1], it[1][0..1]) }
            .let { it.cartesian(it) }
            .filter { (a, b) -> a != b }
            .count { (a, b) -> a.intersects(b, coordinateRange) } / 2

    override fun part2(): Long {
        val (p0, v0) = stones[0]
        val (p1, v1) = stones[1]
        val (p2, v2) = stones[2]

        return (v0.cross() - v1.cross()).appendedVert(v0.cross() - v2.cross())
            .appendedHorz((p1.cross() - p0.cross()).appendedVert(p2.cross() - p0.cross()))
            .appendedHorz((-p0.cross(v0) + p1.cross(v1)).appended(p2.cross(v2) - p0.cross(v0)).transposed())
            .toBigDecimalMatrix()
            .apply { rref() }
            .take(3)
            .sumOf { it.last().toDouble().roundToLong() }
    }
}


fun main() = Day24().run()
