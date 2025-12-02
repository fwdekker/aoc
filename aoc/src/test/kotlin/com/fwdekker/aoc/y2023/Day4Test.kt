package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day4Test : ChallengeTest(
    ::Day4,
    listOf(
        case(part = 1, sample = 1) to 13,
        case(part = 2, sample = 1) to 30,
        case(part = 1) to 20407,
        case(part = 2) to 23806951,
    )
)
