package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day9Test : ChallengeTest(
    ::Day9,
    listOf(
        case(part = 1, sample = 1) to 1928L,
        case(part = 1, sample = 2) to 60L,
        case(part = 2, sample = 1) to 2858L,
        case(part = 1) to 6461289671426L,
        case(part = 2) to 6488291456470L,
    )
)
