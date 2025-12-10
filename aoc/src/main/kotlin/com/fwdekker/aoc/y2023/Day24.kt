package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.asPair
import com.fwdekker.std.collections.asTriple
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.maths.Ray2D
import com.fwdekker.std.maths.T
import com.fwdekker.std.maths.cartesian
import com.fwdekker.std.maths.cat
import com.fwdekker.std.maths.cross
import com.fwdekker.std.maths.horzCat
import com.fwdekker.std.maths.matNeg
import com.fwdekker.std.maths.matPlus
import com.fwdekker.std.maths.rref
import com.fwdekker.std.maths.toBigDecimals
import com.fwdekker.std.maths.vecNeg
import com.fwdekker.std.maths.vecPlus
import com.fwdekker.std.maths.vertCat
import com.fwdekker.std.toLongs
import kotlin.math.roundToLong


// See https://adventofcode.com/2023/day/24
class Day24(
    sample: Int? = null,
    private val coordinateRange: LongRange = 200000000000000L..400000000000000L,
) : Day(year = 2023, day = 24, sample = sample) {
    private val stones = input.linesNotBlank().map { line -> line.split('@').map { it.toLongs(',').asTriple() } }


    override fun part1(): Int =
        stones
            .map { Ray2D(it[0].toList().asPair(), it[1].toList().asPair()) }
            .let { it.cartesian(it) }
            .filter { (a, b) -> a != b }
            .count { (a, b) -> a.intersects(b, coordinateRange) } / 2

    override fun part2(): Long {
        val (p0, v0) = stones[0]
        val (p1, v1) = stones[1]
        val (p2, v2) = stones[2]

        return (v0.cross().matPlus(v1.cross().matNeg())).vertCat(v0.cross().matPlus(v2.cross().matNeg()))
            .horzCat((p1.cross().matPlus(p0.cross().matNeg())).vertCat(p2.cross().matPlus(p0.cross().matNeg())))
            .horzCat((p0.cross(v0).vecNeg().vecPlus(p1.cross(v1))).cat(p2.cross(v2).vecPlus(p0.cross(v0).vecNeg())).T)
            .map { it.toBigDecimals() }
            .rref()
            .take(3)
            .sumOf { it.last().toDouble().roundToLong() }
    }
}


fun main() = Day24().run()
