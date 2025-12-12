package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day21Test : ChallengeTest(
    ::Day21,
    listOf(
        case(part = 1, sample = 1) to 126384L,
        case(part = 1) to 246990L,
        case(part = 2) to 306335137543664L,
    )
)
