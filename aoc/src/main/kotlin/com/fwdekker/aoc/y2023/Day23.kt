package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.aoc.WalledChartGraph
import com.fwdekker.std.grid.Chart
import com.fwdekker.std.grid.Coords
import com.fwdekker.std.grid.allCoordsOf
import com.fwdekker.std.grid.cardinals
import com.fwdekker.std.grid.contains
import com.fwdekker.std.grid.east
import com.fwdekker.std.grid.firstRow
import com.fwdekker.std.grid.get
import com.fwdekker.std.grid.lastRow
import com.fwdekker.std.grid.lastRowIndex
import com.fwdekker.std.grid.north
import com.fwdekker.std.grid.south
import com.fwdekker.std.grid.toChart
import com.fwdekker.std.grid.west
import com.fwdekker.std.maths.Graph
import java.util.ArrayDeque


// See https://adventofcode.com/2023/day/23
class Day23(sample: Int? = null) : Day(year = 2023, day = 23, sample = sample) {
    private val chart = input.toChart()


    override fun part1(): Long = JunctionGraph(chart, base = DumbSlipperyGraph(chart)).longestPath()

    override fun part2(): Long = JunctionGraph(chart, base = DumbDryGraph(chart)).longestPath()
}


fun main() = Day23().run()


class DumbSlipperyGraph(chart: Chart) : WalledChartGraph(chart) {
    override fun getNeighbours(node: Coords): Collection<Coords> =
        when (chart[node]) {
            '#' -> error("Traversed node cannot be a wall.")
            '^' -> listOf(node.north())
            '>' -> listOf(node.east())
            'v' -> listOf(node.south())
            '<' -> listOf(node.west())
            else -> node.cardinals
        }.filter { it in chart && chart[it] != '#' }
}

class DumbDryGraph(chart: Chart) : WalledChartGraph(chart)

private fun <N : Any> Graph<N>.shortestPathLengths(start: N, isEnd: (N) -> Boolean): Map<N, Long> {
    val distances = mutableMapOf<N, Long>()
    val queue = ArrayDeque<N>()

    distances[start] = 0L
    queue.add(start)
    while (queue.isNotEmpty()) {
        val node = queue.removeLast()
        if (isEnd(node)) continue

        val distance = distances.getValue(node)
        getNeighbours(node).forEach { neighbour ->
            val newDistance = distance + getWeight(node, neighbour)
            if (newDistance < (distances[neighbour] ?: Long.MAX_VALUE)) {
                distances[neighbour] = newDistance
                queue.add(neighbour)
            }
        }
    }

    return distances.filterKeys { isEnd(it) }
}


class JunctionGraph(chart: Chart, private val base: Graph<Coords>) : Graph<Coords>() {
    private val start = Coords(0, chart.firstRow.indexOf('.'))
    private val end = Coords(chart.lastRowIndex, chart.lastRow.indexOf('.'))
    private val neighbors = mutableMapOf<Coords, Map<Coords, Long>>()

    override val nodes =
        (listOf(start, end) + chart.allCoordsOf { it != '#' }.filter { base.getNeighbours(it).size > 2 }).toSet()


    private fun calcNeighbors(node: Coords): Map<Coords, Long> =
        neighbors.getOrPut(node) { base.shortestPathLengths(node, isEnd = { it != node && it in nodes }) }

    override fun getNeighbours(node: Coords): Collection<Coords> = calcNeighbors(node).keys

    override fun getWeight(start: Coords, end: Coords): Long = calcNeighbors(start).getValue(end)


    fun longestPath(): Long = allPaths(start, end).maxOf { path -> path.zipWithNext { a, b -> getWeight(a, b) }.sum() }
}
