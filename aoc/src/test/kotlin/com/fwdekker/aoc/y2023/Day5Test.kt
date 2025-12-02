package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day5Test : ChallengeTest(
    ::Day5,
    listOf(
        case(part = 1, sample = 1) to 35L,
        case(part = 2, sample = 1) to 46L,
        case(part = 1) to 579439039L,
        case(part = 2) to 7873084L,
    )
)
