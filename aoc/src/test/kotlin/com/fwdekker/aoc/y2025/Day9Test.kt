package com.fwdekker.aoc.y2025

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2025")
object Day9Test : ChallengeTest(
    ::Day9,
    listOf(
        case(part = 1, sample = 1) to 50L,
        case(part = 2, sample = 1) to 24L,
        case(part = 1) to 4748985168L,
        case(part = 2) to 1550760868L,
    )
)
