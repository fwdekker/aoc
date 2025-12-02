package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day15Test : ChallengeTest(
    ::Day15,
    listOf(
        case(part = 1, sample = 1) to 2028L,
        case(part = 1, sample = 2) to 10092L,
        case(part = 2, sample = 2) to 9021L,
        case(part = 1) to 1471826L,
        case(part = 2) to 1457703L,
    )
)
