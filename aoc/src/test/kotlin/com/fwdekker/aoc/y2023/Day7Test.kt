package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day7Test : ChallengeTest(
    ::Day7,
    listOf(
        case(part = 1, sample = 1) to 6440L,
        case(part = 2, sample = 1) to 5905L,
        case(part = 1) to 248396258L,
        case(part = 2) to 246436046L,
    )
)
