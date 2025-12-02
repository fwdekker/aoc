package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day9Test : ChallengeTest(
    ::Day9,
    listOf(
        case(part = 1, sample = 1) to 114,
        case(part = 2, sample = 1) to 2,
        case(part = 1) to 1939607039,
        case(part = 2) to 1041,
    )
)
