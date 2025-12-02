package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day5Test : ChallengeTest(
    ::Day5,
    listOf(
        case(part = 1, sample = 1) to 143,
        case(part = 2, sample = 1) to 123,
        case(part = 1) to 6051,
        case(part = 2) to 5093,
    )
)
