package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags
import java.math.BigInteger


@Tags("2024")
object Day13Test : ChallengeTest(
    ::Day13,
    listOf(
        case(part = 1, sample = 1) to BigInteger("480"),
        case(part = 1) to BigInteger("29517"),
        case(part = 2) to BigInteger("103570327981381"),
    )
)
