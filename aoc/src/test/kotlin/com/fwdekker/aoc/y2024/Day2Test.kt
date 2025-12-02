package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day2Test : ChallengeTest(
    ::Day2,
    listOf(
        case(part = 1, sample = 1) to 2,
        case(part = 2, sample = 1) to 4,
        case(part = 1) to 334,
        case(part = 2) to 400,
    )
)
