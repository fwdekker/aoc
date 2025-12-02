package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day4Test : ChallengeTest(
    ::Day4,
    listOf(
        case(part = 1, sample = 1) to 4,
        case(part = 1, sample = 2) to 18,
        case(part = 2, sample = 2) to 9,
        case(part = 1) to 2603,
        case(part = 2) to 1965,
    )
)
