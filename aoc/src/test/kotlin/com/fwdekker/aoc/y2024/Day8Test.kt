package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day8Test : ChallengeTest(
    ::Day8,
    listOf(
        case(part = 1, sample = 1) to 2,
        case(part = 1, sample = 2) to 4,
        case(part = 1, sample = 3) to 4,
        case(part = 1, sample = 4) to 14,
        case(part = 2, sample = 5) to 9,
        case(part = 2, sample = 4) to 34,
        case(part = 1) to 303,
        case(part = 2) to 1045,
    )
)
