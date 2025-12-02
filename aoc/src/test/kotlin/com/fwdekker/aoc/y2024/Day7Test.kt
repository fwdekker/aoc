package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags
import java.math.BigInteger


@Tags("2024")
object Day7Test : ChallengeTest(
    ::Day7,
    listOf(
        case(part = 1, sample = 1) to BigInteger("3749"),
        case(part = 2, sample = 1) to BigInteger("11387"),
        case(part = 1) to BigInteger("2501605301465"),
        case(part = 2) to BigInteger("44841372855953"),
    )
)
