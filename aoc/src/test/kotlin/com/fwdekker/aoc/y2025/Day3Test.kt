package com.fwdekker.aoc.y2025

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2025")
object Day3Test : ChallengeTest(
    ::Day3,
    listOf(
        case(part = 1, sample = 1) to 357,
        case(part = 2, sample = 1) to 3121910778619L,
        case(part = 1) to 17109,
        case(part = 2) to 169347417057382L,
    )
)
