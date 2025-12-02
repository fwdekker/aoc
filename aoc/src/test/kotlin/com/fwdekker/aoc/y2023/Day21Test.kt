package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day21Test : ChallengeTest(
    ::Day21,
    listOf(
        case(part = 1) to 3733,
        case(part = 2) to 617729401414635L,
    )
)
