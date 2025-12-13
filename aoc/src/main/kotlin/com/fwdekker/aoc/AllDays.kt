package com.fwdekker.aoc

import com.fwdekker.std.AllChallenges


fun main() = AllChallenges(
    type = "Day",
    nickname = {
        val year = it.removePrefix("com.fwdekker.aoc.y").takeWhile { it.isDigit() }
        val day = it.takeLastWhile { it.isDigit() }
        "Day $year.$day"
    },
    filter = { it.matches(Regex("com\\.fwdekker\\.aoc\\.y\\d+\\.Day\\d+")) && !it.contains("y9999") },
).run()
