package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day20Test : ChallengeTest(
    { sample ->
        // Samples 1 and 2 are the same, but differ in the `minSavings`
        when (sample) {
            1 -> Day20(sample, minSavings = 1)
            2 -> Day20(sample, minSavings = 50)
            else -> Day20(sample)
        }
    },
    listOf(
        case(part = 1, sample = 1) to 44,
        case(part = 2, sample = 2) to 285,
        case(part = 1) to 1317,
        case(part = 2) to 982474,
    )
)
