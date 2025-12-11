package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.map
import com.fwdekker.std.collections.mapFirst
import com.fwdekker.std.collections.mapSecond
import com.fwdekker.std.collections.repeat
import com.fwdekker.std.collections.unzip
import com.fwdekker.std.read
import com.fwdekker.std.toInts


// See https://adventofcode.com/2024/day/9
class Day9(sample: Int? = null) : Day(year = 2024, day = 9, sample = sample) {
    private val diskMap = input.toInts("")


    override fun part1(): Long {
        val (files, frees) = blocks()

        frees.indices.forEach { freeIdx ->
            while (true) {
                if (freeIdx >= files.lastIndex)
                    break

                val free = frees[freeIdx]
                val used = free.takeWhile { it != -1 }
                val available = free.size - used.size
                val file = files.last()

                if (file.size == available) {
                    files.removeLast()
                    frees[freeIdx] = used + file
                    break
                } else if (file.size < available) {
                    files.removeLast()
                    frees[freeIdx] = used + file + free.drop(used.size + file.size)
                } else {
                    files[files.lastIndex] = file.drop(available)
                    frees[freeIdx] = used + file.take(available)
                    break
                }
            }
        }

        return files.unzip(frees).flatten().checksum()
    }

    override fun part2(): Long {
        val (files, frees) = blocks()

        files.indices.reversed().forEach { fileIdx ->
            val file = files[fileIdx]

            val freeIdx = frees.indexOfFirst { free ->
                free.size >= file.size && -1 in free && free.count { it == -1 } >= file.size
            }
            if (freeIdx !in 0..<fileIdx) return@forEach
            val free = frees[freeIdx]
            val used = free.count { it != -1 }

            files[fileIdx] = List(file.size) { -1 }
            frees[freeIdx] = free.take(used) + file + free.drop(used + file.size)
        }

        return files.unzip(frees).flatten().checksum()
    }


    private fun blocks(): Pair<MutableList<List<Int>>, MutableList<List<Int>>> =
        diskMap.withIndex()
            .partition { (idx, _) -> idx % 2 == 0 }
            .map { list -> list.map { it.value } }
            .mapFirst { files -> files.mapIndexed { id, len -> listOf(id).repeat(len) }.toMutableList() }
            .mapSecond { free -> free.map { len -> listOf(-1).repeat(len) }.toMutableList() }

    private fun Collection<Int>.checksum(): Long =
        withIndex().filter { (_, it) -> it != -1 }.sumOf { (id, value) -> id * value.toLong() }
}


fun main() = Day9().run()
