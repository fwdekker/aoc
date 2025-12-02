package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day17Test : ChallengeTest(
    ::Day17,
    listOf(
        case(part = 1, sample = 1) to "4,6,3,5,6,3,5,2,1,0",
        case(part = 2, sample = 2) to 117440L,
        case(part = 1) to "4,0,4,7,1,2,7,1,6",
        case(part = 2) to 202322348616234L,
    )
)
