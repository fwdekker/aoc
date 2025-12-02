package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day6Test : ChallengeTest(
    ::Day6,
    listOf(
        case(part = 1, sample = 1) to 288L,
        case(part = 2, sample = 1) to 71503L,
        case(part = 1) to 114400L,
        case(part = 2) to 21039729L,
    )
)
