package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day22Test : ChallengeTest(
    ::Day22,
    listOf(
        case(part = 1, sample = 1) to 5,
        case(part = 2, sample = 1) to 7,
        case(part = 1) to 507,
        case(part = 2) to 51733,
    )
)
