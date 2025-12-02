package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day19Test : ChallengeTest(
    ::Day19,
    listOf(
        case(part = 1, sample = 1) to 6,
        case(part = 2, sample = 1) to 16L,
        case(part = 1) to 242,
        case(part = 2) to 595975512785325L,
    )
)
