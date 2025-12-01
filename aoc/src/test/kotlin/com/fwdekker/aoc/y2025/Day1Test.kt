package com.fwdekker.aoc.y2025

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.DayTest


/**
 * Tests for [Day1].
 */
object Day1Test : DayTest(
    ::Day1,
    listOf(
        Triple(Day.resource(2025, 1, sample = 1), Day::part1, 3),
        Triple(Day.resource(2025, 1, sample = 1), Day::part2, 6),
        Triple(Day.resource(2025, 1), Day::part1, 1026),
        Triple(Day.resource(2025, 1), Day::part2, 5923),
    )
)
