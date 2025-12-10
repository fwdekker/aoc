package com.fwdekker.aoc.y2025

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.asTriple
import com.fwdekker.std.collections.mapAt
import com.fwdekker.std.collections.sorted
import com.fwdekker.std.collections.without
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.maths.Vec3
import com.fwdekker.std.maths.compareTo
import com.fwdekker.std.maths.distance
import com.fwdekker.std.maths.productOf
import com.fwdekker.std.read
import com.fwdekker.std.toLongs


// See https://adventofcode.com/2025/day/8
class Day8(sample: Int? = null, private val connections: Int = 1_000) : Day(year = 2025, day = 8, sample = sample) {
    private val boxes =
        read(resource).linesNotBlank().map { it.toLongs(',').asTriple() }
    private val spanning =
        boxes
            .flatMapIndexed { idx, box -> boxes.drop(idx + 1).map { box to it } }
            .sortedBy { it.first.distance(it.second) }
            .let { pairs ->
                val seen = mutableSetOf<Vec3<Long>>()

                pairs.takeWhile { (from, to) ->
                    (seen.size != boxes.size).also {
                        seen += from
                        seen += to
                    }
                }
            }


    override fun part1(): Long =
        spanning
            .distinctBy { it.sorted { p1, p2 -> p1.compareTo(p2) } }
            .take(connections)
            .fold(listOf<Set<Vec3<Long>>>()) { circuits, (from, to) ->
                val fromIdx = circuits.indexOfFirst { it.contains(from) }
                val toIdx = circuits.indexOfFirst { it.contains(to) }

                if (fromIdx < 0 && toIdx < 0) circuits.plus(element = setOf(from, to))
                else if (fromIdx < 0) circuits.mapAt(toIdx) { it + from }
                else if (toIdx < 0) circuits.mapAt(fromIdx) { it + to }
                else if (fromIdx != toIdx) circuits.mapAt(fromIdx) { it + circuits[toIdx] }.without(toIdx)
                else circuits
            }
            .sortedByDescending { it.size }
            .take(3)
            .productOf { it.size }

    override fun part2(): Long =
        spanning.last().let { it.first.first * it.second.first }
}


fun main() = Day8().run()
