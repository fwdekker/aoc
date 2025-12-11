package com.fwdekker.aoc.y9999

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Ignored
import io.kotest.core.annotation.Tags


@Ignored
@Tags("9999")
object Day999Test : ChallengeTest(
    ::Day999,
    listOf(
        case(part = 1, sample = 1) to -1,
        case(part = 2, sample = 1) to -1,
        case(part = 1) to -1,
        case(part = 2) to -1,
    )
)
