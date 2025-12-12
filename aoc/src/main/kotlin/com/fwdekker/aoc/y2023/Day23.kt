package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
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
import java.util.PriorityQueue


// See https://adventofcode.com/2023/day/23
class Day23(sample: Int? = null) : Day(year = 2023, day = 23, sample = sample) {
    private val chart = input.toChart()

    private val start = Coords(0, chart.firstRow.indexOf('.'))
    private val end = Coords(chart.lastRowIndex, chart.lastRow.indexOf('.'))
    private val junctions =
        listOf(start, end) + chart.allCoordsOf { it != '#' }.filter { chart.dryNeighborsOf(it).size > 2 }


    override fun part1(): Int = chart.longestDistance(start, end) { chart.slipperyNeighborsOf(it) }

    // TODO: Make this one more time-efficient, currently takes more than ten seconds!
    // TODO: Using a cache?
    override fun part2(): Int = chart.longestDistance(start, end) { chart.dryNeighborsOf(it) }


    private fun Chart.slipperyNeighborsOf(coords: Coords): List<Coords> =
        when (this[coords]) {
            '#' -> error("Traversed node cannot be a wall.")
            '^' -> listOf(coords.north())
            '>' -> listOf(coords.east())
            'v' -> listOf(coords.south())
            '<' -> listOf(coords.west())
            else -> coords.cardinals
        }.filter { it in this && this[it] != '#' }

    private fun Chart.dryNeighborsOf(coords: Coords): List<Coords> =
        coords.cardinals.filter { it in this && this[it] != '#' }

    private fun Chart.longestDistance(from: Coords, to: Coords, getNeighbors: (Coords) -> Iterable<Coords>): Int =
        longestDistance(from, to, junctions.associateWith { findShortestDistances(it, junctions, getNeighbors) })

    private fun Chart.longestDistance(
        from: Coords,
        to: Coords,
        edges: Map<Coords, Map<Coords, Int>>,
        history: Set<Coords> = setOf(from),
    ): Int =
        edges.getValue(from)
            .filter { (it, _) -> it !in history }
            .maxOfOrNull { (it, dist) -> if (it == to) dist else dist + longestDistance(it, to, edges, history + it) }
            ?: Integer.MIN_VALUE

    private fun findShortestDistances(
        from: Coords,
        to: List<Coords>,
        getNeighbors: (Coords) -> Iterable<Coords>,
    ): Map<Coords, Int> {
        val distances = mutableMapOf<Coords, Int>().withDefault { Integer.MAX_VALUE }
        distances[from] = 0

        val queue = PriorityQueue<Coords> { o1, o2 -> distances.getValue(o1) - distances.getValue(o2) }
        queue.add(from)

        while (queue.isNotEmpty()) {
            val current = queue.remove()
            if (current != from && current in to) continue

            val distance = distances.getValue(current)
            getNeighbors(current)
                .filter { it !in distances }
                .forEach { nextStep ->
                    if (distance + 1 < distances.getValue(nextStep)) {
                        distances[nextStep] = distance + 1
                        queue.add(nextStep)
                    }
                }
        }

        return distances.filterKeys { it in to }
    }
}


fun main() = Day23().run()
