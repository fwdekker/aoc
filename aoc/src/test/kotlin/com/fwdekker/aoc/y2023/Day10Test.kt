package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day10Test : ChallengeTest(
    ::Day10,
    listOf(
        case(part = 1, sample = 1) to 4,
        case(part = 1, sample = 2) to 8,
        case(part = 2, sample = 3) to 4,
        case(part = 2, sample = 4) to 4,
        case(part = 2, sample = 5) to 8,
        case(part = 2, sample = 6) to 10,
        case(part = 1) to 6815,
        case(part = 2) to 269,
    )
)
