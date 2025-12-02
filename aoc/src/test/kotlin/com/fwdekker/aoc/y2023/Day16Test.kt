package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day16Test : ChallengeTest(
    ::Day16,
    listOf(
        case(part = 1, sample = 1) to 46,
        case(part = 2, sample = 1) to 51,
        case(part = 1) to 7046,
        case(part = 2) to 7313,
    )
)
