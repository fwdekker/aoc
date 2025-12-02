package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day1Test : ChallengeTest(
    ::Day1,
    listOf(
        case(part = 1, sample = 1) to 142,
        case(part = 2, sample = 2) to 281,
        case(part = 1) to 56042,
        case(part = 2) to 55358,
    )
)
