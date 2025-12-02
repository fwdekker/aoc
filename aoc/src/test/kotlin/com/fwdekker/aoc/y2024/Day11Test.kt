package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day11Test : ChallengeTest(
    ::Day11,
    listOf(
        case(part = 1, sample = 1) to 7L,
        case(part = 1, sample = 2) to 55312L,
        case(part = 1) to 216996L,
        case(part = 2) to 257335372288947L,
    )
)
