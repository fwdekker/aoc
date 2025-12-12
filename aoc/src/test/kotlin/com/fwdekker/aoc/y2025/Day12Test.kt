package com.fwdekker.aoc.y2025

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2025")
object Day12Test : ChallengeTest(
    ::Day12,
    listOf(
        case(part = 1, sample = 1) to 2,
        case(part = 1) to 497,
    )
)
