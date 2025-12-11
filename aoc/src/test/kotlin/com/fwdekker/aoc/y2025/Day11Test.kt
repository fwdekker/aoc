package com.fwdekker.aoc.y2025

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2025")
object Day11Test : ChallengeTest(
    ::Day11,
    listOf(
        case(part = 1, sample = 1) to 5,
        case(part = 2, sample = 2) to 2L,
        case(part = 1) to 590,
        case(part = 2) to 319473830844560L,
    )
)
