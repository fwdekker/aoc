package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.asPair
import com.fwdekker.std.collections.asTriple
import com.fwdekker.std.collections.neverNull
import com.fwdekker.std.collections.swap
import com.fwdekker.std.memoised


// See https://adventofcode.com/2024/day/23
class Day23(sample: Int? = null) : Day(year = 2024, day = 23, sample = sample) {
    private val edges = input.lines()
        .flatMap { edge -> edge.split('-').asPair().let { listOf(it, it.swap()) } }
        .groupBy({ it.first }, { it.second })
        .mapValues { it.value.toSet() }
        .neverNull()


    override fun part1() =
        edges.keys
            .filter { u -> u[0] == 't' }
            .flatMap { u ->
                edges[u]
                    .flatMap { v ->
                        edges[v]
                            .filter { w -> u in edges[w] }
                            .map { w -> listOf(u, v, w).sorted().asTriple() }
                    }
            }
            .distinct()
            .size

    override fun part2() = find(emptySet()).sorted().joinToString(",")


    private val find = memoised { nodes: Set<String> ->
        val mutuals = nodes.fold(edges.keys) { acc, node -> acc.intersect(edges[node] + node) }

        if (mutuals.isEmpty()) nodes
        else if (mutuals.intersect(nodes).size < nodes.size) emptySet()
        else if (mutuals.size == nodes.size) nodes
        else mutuals
            .map { mutual -> nodes + mutual }
            .distinct()
            .let { if (nodes in it) it.minusElement(nodes) else it }
            .map { callRecursive(it) }
            .maxByOrNull { it.size }
            ?: nodes
    }
}


fun main() = Day23().run()
