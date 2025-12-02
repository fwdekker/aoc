package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day13Test : ChallengeTest(
    ::Day13,
    listOf(
        case(part = 1, sample = 1) to 405,
        case(part = 2, sample = 1) to 400,
        case(part = 1) to 33728,
        case(part = 2) to 28235,
    )
)
