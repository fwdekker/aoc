package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day15Test : ChallengeTest(
    ::Day15,
    listOf(
        case(part = 1, sample = 1) to 1320,
        case(part = 2, sample = 1) to 145,
        case(part = 1) to 511343,
        case(part = 2) to 294474,
    )
)
