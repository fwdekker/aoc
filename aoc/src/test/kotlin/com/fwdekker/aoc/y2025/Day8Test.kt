package com.fwdekker.aoc.y2025

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2025")
object Day8Test : ChallengeTest(
    { sample ->
        if (sample == 1) Day8(sample, connections = 10)
        else Day8(sample)
    },
    listOf(
        case(part = 1, sample = 1) to 40L,
        case(part = 2, sample = 1) to 25272L,
        case(part = 1) to 123420L,
        case(part = 2) to 673096646L,
    )
)
