package com.fwdekker.aoc.y2025

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.DayTest


/**
 * Tests for [Day2].
 */
object Day2Test : DayTest(
    ::Day2,
    listOf(
        Triple(Day.resource(2025, 2, sample = 1), Day::part1, 1227775554L),
        Triple(Day.resource(2025, 2, sample = 1), Day::part2, 4174379265L),
        Triple(Day.resource(2025, 2), Day::part1, 29940924880L),
        Triple(Day.resource(2025, 2), Day::part2, 48631958998L),
    )
)
