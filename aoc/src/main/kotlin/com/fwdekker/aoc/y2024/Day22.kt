package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.associateWithIndex
import com.fwdekker.std.toLongs


// See https://adventofcode.com/2024/day/22
class Day22(sample: Int? = null) : Day(year = 2024, day = 22, sample = sample) {
    private val seeds = input.toLongs('\n')
    private val monkeys = seeds.indices
    private val samples = 2000
    private val windowSize = 4


    override fun part1() = seeds.sumOf { prng(it).drop(samples).first() }

    // TODO: Improve memory efficiency
    override fun part2(): Long {
        val prices = seeds.map { seed -> prng(seed).take(samples).map { it % 10L }.toList() }
        val seqs = prices.map { it.zipWithNext { a, b -> b - a }.windowed(windowSize) }
        val seqToIdx = seqs.map { sequence -> sequence.asReversed().associateWithIndex() }

        return seqs
            .flatten().distinct()
            .maxOf { seq ->
                monkeys.sumOf { monkey ->
                    seqToIdx[monkey][seq]?.let { prices[monkey][samples - 1 - it] } ?: 0L
                }
            }
    }


    private fun prng(seed: Long): Sequence<Long> =
        generateSequence(seed) { secret ->
            val step1 = (secret xor secret * 64L) % 16777216L
            val step2 = (step1 xor step1 / 32L) % 16777216L
            val step3 = (step2 xor step2 * 2048L) % 16777216L
            step3
        }
}


fun main() = Day22().run()
