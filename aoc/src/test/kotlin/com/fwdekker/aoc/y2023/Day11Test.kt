package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day11Test : ChallengeTest(
    ::Day11,
    listOf(
        case(part = 1, sample = 1) to 374L,
        case(part = 1) to 9795148L,
        case(part = 2) to 650672493820L,
    )
)
