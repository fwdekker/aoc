package com.fwdekker.aoc.y2025

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2025")
object Day4Test : ChallengeTest(
    ::Day4,
    listOf(
        case(part = 1, sample = 1) to 13,
        case(part = 2, sample = 1) to 43,
        case(part = 1) to 1604,
        case(part = 2) to 9397,
    )
)
