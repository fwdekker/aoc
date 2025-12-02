package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day10Test : ChallengeTest(
    ::Day10,
    listOf(
        case(part = 1, sample = 1) to 36,
        case(part = 2, sample = 1) to 81,
        case(part = 1) to 607,
        case(part = 2) to 1384,
    )
)
