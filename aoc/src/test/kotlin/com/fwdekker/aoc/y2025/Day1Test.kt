package com.fwdekker.aoc.y2025

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2025")
object Day1Test : ChallengeTest(
    ::Day1,
    listOf(
        case(part = 1, sample = 1) to 3,
        case(part = 2, sample = 1) to 6,
        case(part = 1) to 1026,
        case(part = 2) to 5923,
    )
)
