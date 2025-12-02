package com.fwdekker.aoc.y2023

import com.fwdekker.std.ChallengeTest
import io.kotest.core.annotation.Tags


@Tags("2023")
object Day24Test : ChallengeTest(
    { sample ->
        if (sample == 1) Day24(sample, coordinateRange = 7L..27L)
        else Day24(sample)
    },
    listOf(
        case(part = 1, sample = 1) to 2L,
        case(part = 2, sample = 1) to 47L,
        case(part = 1) to 16727L,
        case(part = 2) to 606772018765659L,
    )
)
