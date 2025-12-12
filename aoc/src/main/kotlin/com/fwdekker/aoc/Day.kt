package com.fwdekker.aoc

import com.fwdekker.std.Challenge


/**
 * Convenience class for invoking the code for any particular day and part with a given resource.
 *
 * @property year the year of advent of code this day belongs to
 * @property day the day of advent of code this object corresponds to
 * @property sample the sample number to solve for, or `null` to use the full problem
 */
abstract class Day(val year: Int, val day: Int, val sample: Int? = null) : Challenge(2) {
    override val resource: String get() =
        "y$year/Day${day}"
            .let { if (sample != null) "${it}Sample${sample}" else it }
            .let { "${it}.txt" }


    override fun runPart(number: Int): Any? =
        when (number) {
            1 -> part1()
            2 -> part2()
            else -> error("Invalid part number $number.")
        }

    abstract fun part1(): Any?

    abstract fun part2(): Any?
}
