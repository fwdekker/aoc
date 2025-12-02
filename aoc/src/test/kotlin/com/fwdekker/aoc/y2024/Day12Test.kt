package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day12Test : ChallengeTest(
    ::Day12,
    listOf(
        case(part = 1, sample = 1) to 140L,
        case(part = 1, sample = 2) to 772L,
        case(part = 1, sample = 3) to 1930L,
        case(part = 2, sample = 1) to 80L,
        case(part = 2, sample = 2) to 436L,
        case(part = 2, sample = 3) to 1206L,
        case(part = 2, sample = 4) to 236L,
        case(part = 2, sample = 5) to 368L,
        case(part = 1) to 1473408L,
        case(part = 2) to 886364L,
    )
)
