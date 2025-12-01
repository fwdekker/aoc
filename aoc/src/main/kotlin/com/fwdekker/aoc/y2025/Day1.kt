package com.fwdekker.aoc.y2025

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.mapSeconds
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.maths.toInt
import com.fwdekker.std.maths.wrapMod
import com.fwdekker.std.read
import com.fwdekker.std.splitAtIndex


class Day1(resource: String = resource(2025, 1)) : Day() {
    private val pairs = read(resource).linesNotBlank().map { it.splitAtIndex(1) }.mapSeconds { it.toInt() }


    override fun part1(): Int =
        pairs.runningFold(50) { pointing, (direction, distance) ->
            (pointing + (if (direction == "L") -1 else 1) * distance).wrapMod(100)
        }.count { it == 0 }

    override fun part2(): Int {
        return pairs.fold(50 to 0) { (oldPointing, oldCount), (direction, distance) ->
            require(distance != 0) { "Undefined behaviour when a rotation has zero clicks." }

            val newPointing = (oldPointing + (if (direction == "L") -1 else 1) * distance).wrapMod(100)
            val goesPastZero = oldPointing != 0 &&
                (newPointing == 0 ||
                    direction == "L" && newPointing > oldPointing ||
                    direction == "R" && newPointing < oldPointing)
            newPointing to oldCount + distance / 100 + goesPastZero.toInt()
        }.second
    }
}


fun main() = Day1().run()
