package com.fwdekker.aoc.y2025

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2025")
object Day10Test : ChallengeTest(
    ::Day10,
    listOf(
        case(part = 1, sample = 1) to 7,
        case(part = 2, sample = 1) to 33,
        case(part = 1) to 505,
        case(part = 2) to 20002,
    )
)
