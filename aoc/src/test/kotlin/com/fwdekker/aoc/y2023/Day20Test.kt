package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day20Test : ChallengeTest(
    ::Day20,
    listOf(
        case(part = 1, sample = 1) to 32000000L,
        case(part = 1, sample = 2) to 11687500L,
        case(part = 1) to 825167435L,
        case(part = 2) to 225514321828633L,
    )
)
