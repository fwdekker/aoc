package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day14Test : ChallengeTest(
    { sample ->
        if (sample == 1) Day14(sample, width = 11, height = 7)
        else Day14(sample)
    },
    listOf(
        case(part = 1, sample = 1) to 12,
        case(part = 1) to 217328832,
        case(part = 2) to 7412,
    )
)
