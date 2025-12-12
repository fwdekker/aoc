package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.foldSum
import com.fwdekker.std.toLongs


// See https://adventofcode.com/2024/day/22
class Day22(sample: Int? = null) : Day(year = 2024, day = 22, sample = sample) {
    private val seeds = input.toLongs('\n')
    private val samples = 2_000
    private val windowSize = 4


    override fun part1() = seeds.sumOf { prng(it).drop(samples).first() }

    override fun part2(): Long =
        seeds.asSequence()
            .map { seed ->
                prng(seed).take(samples).map { it % 10L }
                    .windowed(windowSize + 1)
                    .map { it.zipWithNext { a, b -> b - a }.joinToString(",") to it.last() }
                    .distinctBy { (sequence, _) -> sequence }
                    .associate { (sequence, price) -> sequence to price }
            }
            .foldSum { a, b -> (a ?: 0L) + b }
            .maxOf { it.value }


    private fun prng(seed: Long): Sequence<Long> =
        generateSequence(seed) { secret ->
            val step1 = (secret xor secret * 64L) % 16777216L
            val step2 = (step1 xor step1 / 32L) % 16777216L
            val step3 = (step2 xor step2 * 2048L) % 16777216L
            step3
        }
}


fun main() = Day22().run()
