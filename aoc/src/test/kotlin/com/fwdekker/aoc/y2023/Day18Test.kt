package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day18Test : ChallengeTest(
    ::Day18,
    listOf(
        case(part = 1, sample = 1) to 62L,
        case(part = 2, sample = 1) to 952408144115L,
        case(part = 1) to 40714L,
        case(part = 2) to 129849166997110L,
    )
)
