package com.fwdekker.aoc.y2025

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2025")
object Day5Test : ChallengeTest(
    ::Day5,
    listOf(
        case(part = 1, sample = 1) to 3,
        case(part = 2, sample = 1) to 14L,
        case(part = 1) to 520,
        case(part = 2) to 347338785050515L,
    )
)
