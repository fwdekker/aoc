package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day1Test : ChallengeTest(
    ::Day1,
    listOf(
        case(part = 1, sample = 1) to 11,
        case(part = 2, sample = 1) to 31,
        case(part = 1) to 1590491,
        case(part = 2) to 22588371,
    )
)
