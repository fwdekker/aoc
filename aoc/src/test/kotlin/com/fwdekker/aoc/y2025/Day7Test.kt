package com.fwdekker.aoc.y2025

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2025")
object Day7Test : ChallengeTest(
    ::Day7,
    listOf(
        case(part = 1, sample = 1) to 21,
        case(part = 2, sample = 1) to 40L,
        case(part = 1) to 1628,
        case(part = 2) to 27055852018812L,
    )
)
