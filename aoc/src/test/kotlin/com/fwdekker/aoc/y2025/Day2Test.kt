package com.fwdekker.aoc.y2025

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2025")
object Day2Test : ChallengeTest(
    ::Day2,
    listOf(
        case(part = 1, sample = 1) to 1227775554L,
        case(part = 2, sample = 1) to 4174379265L,
        case(part = 1) to 29940924880L,
        case(part = 2) to 48631958998L,
    )
)
