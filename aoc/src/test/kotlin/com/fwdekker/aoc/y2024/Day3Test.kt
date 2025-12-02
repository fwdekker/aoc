package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day3Test : ChallengeTest(
    ::Day3,
    listOf(
        case(part = 1, sample = 1) to 161,
        case(part = 2, sample = 2) to 48,
        case(part = 1) to 169021493,
        case(part = 2) to 111762583,
    )
)
