package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.asPair
import com.fwdekker.std.collections.mapSeconds
import com.fwdekker.std.collections.repeat
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.repeat
import com.fwdekker.std.toInts


// See https://adventofcode.com/2023/day/12
class Day12(sample: Int? = null) : Day(year = 2023, day = 12, sample = sample) {
    private val lines = input.linesNotBlank()
    private val rows = lines.map { it.split(' ').asPair() }.mapSeconds { it.toInts(',') }


    override fun part1(): Long = rows.sumOf { combosOf(it.first, it.second) }

    override fun part2(): Long = rows.sumOf { combosOf(it.first.repeat(5, "?"), it.second.repeat(5)) }


    private fun <K> Map<K, Long>.plusAtKeys(that: Map<K, Long>): Map<K, Long> =
        toMutableMap().also { that.forEach { (k, v) -> it[k] = (it[k] ?: 0) + v } }

    private fun String.fits(offset: Int, size: Int): Boolean {
        require(size > 0) { "Size must be strictly positive." }

        return (offset + size in 0..this.length) &&
            getOrNull(offset - 1) != '#' &&
            getOrNull(offset + size) != '#' &&
            slice(offset..<offset + size).all { it in "#?" }
    }

    private fun combosOf(record: String, offset: Int, group: Int): List<Int> {
        val end = record.indexOf('#', offset).let { if (it >= 0) it else record.length }
        return (offset..end).filter { record.fits(it, group) }.map { it + group + 1 }
    }

    private fun combosOf(record: String, groups: Collection<Int>): Long =
        groups
            .fold(mapOf(0 to 1L)) { accumulator, group ->
                accumulator
                    .map { (offset, combos) -> combosOf(record, offset, group).associateWith { combos } }
                    .fold(emptyMap<Int, Long>()) { acc, it -> acc.plusAtKeys(it) }
                    .toMap()
            }
            .filterKeys { '#' !in record.drop(it) }
            .values.sum()
}


fun main() = Day12().run()
