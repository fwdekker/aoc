package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day25Test : ChallengeTest(
    ::Day25,
    listOf(
        case(part = 1, sample = 1) to 54,
        case(part = 1) to 606062,
    )
)
