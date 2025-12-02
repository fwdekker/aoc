package com.fwdekker.aoc.y2024

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2024")
object Day23Test : ChallengeTest(
    ::Day23,
    listOf(
        case(part = 1, sample = 1) to 7,
        case(part = 2, sample = 1) to "co,de,ka,ta",
        case(part = 1) to 1306,
        case(part = 2) to "bd,dk,ir,ko,lk,nn,ob,pt,te,tl,uh,wj,yl",
    )
)
