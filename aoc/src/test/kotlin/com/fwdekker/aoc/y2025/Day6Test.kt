package com.fwdekker.aoc.y2025

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2025")
object Day6Test : ChallengeTest(
    ::Day6,
    listOf(
        case(part = 1, sample = 1) to 4277556L,
        case(part = 2, sample = 1) to 3263827L,
        case(part = 1) to 4076006202939L,
        case(part = 2) to 7903168391557L,
    )
)
