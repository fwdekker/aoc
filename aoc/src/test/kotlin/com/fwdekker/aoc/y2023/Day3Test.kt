package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day3Test : ChallengeTest(
    ::Day3,
    listOf(
        case(part = 1, sample = 1) to 4361,
        case(part = 2, sample = 1) to 467835L,
        case(part = 1) to 535078,
        case(part = 2) to 75312571L,
    )
)
