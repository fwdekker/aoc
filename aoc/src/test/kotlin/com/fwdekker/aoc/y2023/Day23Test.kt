package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day23Test : ChallengeTest(
    ::Day23,
    listOf(
        case(part = 1, sample = 1) to 94,
        case(part = 2, sample = 1) to 154,
        case(part = 1) to 2318,
        case(part = 2) to 6426,
    )
)
