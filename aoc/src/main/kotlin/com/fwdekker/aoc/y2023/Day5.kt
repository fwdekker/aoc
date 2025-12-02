package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.sum
import com.fwdekker.std.maths.overlap
import com.fwdekker.std.maths.shift
import com.fwdekker.std.maths.without
import com.fwdekker.std.read
import com.fwdekker.std.sections
import com.fwdekker.std.toLongs


// See https://adventofcode.com/2023/day/5
class Day5(sample: Int? = null) : Day(year = 2023, day = 5, sample = sample) {
    private val sections = read(resource).sections()

    private val seeds: Collection<Long> = sections[0][0].substringAfter(": ").toLongs(' ')
    private val seedRanges: Collection<LongRange> = seeds.chunked(2).map { it[0]..<it[0] + it[1] }


    override fun part1(): Long =
        sections.drop(1)
            .fold(seeds) { categories, lines ->
                lines.drop(1)
                    .map { it.asMapper() }
                    .fold(categories.associateWith { it }) { acc, mapper -> acc.mapValues(mapper) }
                    .values
            }
            .min()

    override fun part2(): Long =
        sections.drop(1)
            .fold(seedRanges) { categories, lines ->
                lines.drop(1)
                    .map { it.asRangeMapper() }
                    .fold(categories.associateWith { it }) { acc, mapper -> acc.map(mapper).sum() }
                    .values
            }
            .minOf { it.first }


    private fun String.asMapper(): (Map.Entry<Long, Long>) -> Long {
        val (dstStart, srcStart, length) = toLongs(' ')
        return { (src, oldDst) -> if (src - srcStart in 0..<length) src - srcStart + dstStart else oldDst }
    }

    private fun String.asRangeMapper(): (Map.Entry<LongRange, LongRange>) -> Map<LongRange, LongRange> {
        val (dstStart, srcStart, length) = toLongs(' ')

        return { (src, oldDst) ->
            if (src.first != oldDst.first) {
                mapOf(src to oldDst)
            } else {
                val srcRange = (srcStart..<srcStart + length)
                val applicable = src.overlap(srcRange)
                val inapplicable = src.without(srcRange)

                val newDst = inapplicable.associateWith { it }
                if (applicable.isEmpty()) newDst
                else newDst + Pair(applicable, applicable.shift(dstStart - srcStart))
            }
        }
    }
}


fun main() = Day5().run()
