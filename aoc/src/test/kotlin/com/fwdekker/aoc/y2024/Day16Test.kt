package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day16Test : ChallengeTest(
    ::Day16,
    listOf(
        case(part = 1, sample = 1) to 7036L,
        case(part = 1, sample = 2) to 11048L,
        case(part = 2, sample = 1) to 45,
        case(part = 2, sample = 2) to 64,
        case(part = 1) to 122492L,
        case(part = 2) to 520,
    )
)
