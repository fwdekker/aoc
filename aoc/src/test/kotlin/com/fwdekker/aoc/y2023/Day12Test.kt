package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day12Test : ChallengeTest(
    ::Day12,
    listOf(
        case(part = 1, sample = 1) to 21L,
        case(part = 2, sample = 1) to 525152L,
        case(part = 1) to 7674L,
        case(part = 2) to 4443895258186L,
    )
)
