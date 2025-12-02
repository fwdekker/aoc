package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day8Test : ChallengeTest(
    ::Day8,
    listOf(
        case(part = 1, sample = 1) to 2L,
        case(part = 1, sample = 2) to 6L,
        case(part = 2, sample = 3) to 6L,
        case(part = 1) to 19783L,
        case(part = 2) to 9177460370549L,
    )
)
