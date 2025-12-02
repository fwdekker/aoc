package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day17Test : ChallengeTest(
    ::Day17,
    listOf(
        case(part = 1, sample = 1) to 102,
        case(part = 2, sample = 1) to 94,
        case(part = 2, sample = 2) to 71,
        case(part = 1) to 814,
        case(part = 2) to 974,
    )
)
