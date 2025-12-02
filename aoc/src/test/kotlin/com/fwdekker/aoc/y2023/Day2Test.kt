package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day2Test : ChallengeTest(
    ::Day2,
    listOf(
        case(part = 1, sample = 1) to 8,
        case(part = 2, sample = 1) to 2286,
        case(part = 1) to 2449,
        case(part = 2) to 63981,
    )
)
