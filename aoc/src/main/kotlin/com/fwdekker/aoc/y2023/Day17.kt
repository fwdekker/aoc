package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.grid.Cardinal
import com.fwdekker.std.grid.Chart
import com.fwdekker.std.grid.Coords
import com.fwdekker.std.grid.Direction
import com.fwdekker.std.grid.Heading
import com.fwdekker.std.grid.contains
import com.fwdekker.std.grid.get
import com.fwdekker.std.grid.lastColIndex
import com.fwdekker.std.grid.lastRowIndex
import com.fwdekker.std.grid.toChart
import com.fwdekker.std.read
import java.util.PriorityQueue


// See https://adventofcode.com/2023/day/17
class Day17(sample: Int? = null) : Day(year = 2023, day = 17, sample = sample) {
    private val chart = input.toChart()


    override fun part1(): Int = chart.smallestLoss({ it.neighbors() }, { true })

    override fun part2(): Int = chart.smallestLoss({ it.ultraNeighbors() }, { it.times >= 4 })


    private data class Step(val heading: Heading, val times: Int) {
        val coords: Coords get() = heading.coords
        val direction: Direction get() = heading.direction

        private fun move(direction: Direction): Step =
            Step(heading.go { direction }, if (direction == this.direction) times + 1 else 1)

        fun neighbors(): List<Step> =
            Cardinal.entries
                .filter { (it != direction || times < 3) && it != direction.behind }
                .map { move(it) }

        fun ultraNeighbors(): List<Step> =
            Cardinal.entries
                .filter { (if (it == direction) times < 10 else times >= 4) && it != direction.behind }
                .map { move(it) }
    }

    private fun Chart.smallestLoss(getNeighbors: (Step) -> Iterable<Step>, validEnd: (Step) -> Boolean): Int {
        val distances = mutableMapOf<Step, Int>().withDefault { Integer.MAX_VALUE }
        val queue = PriorityQueue<Step> { o1, o2 -> distances.getValue(o1) - distances.getValue(o2) }

        val end = Coords(lastRowIndex, lastColIndex)

        listOf(com.fwdekker.std.grid.South, com.fwdekker.std.grid.East)
            .map { Step(Heading(0, 0, it), 0) }
            .forEach {
                distances[it] = 0
                queue.add(it)
            }

        while (queue.isNotEmpty()) {
            val current = queue.remove()
            if (current.coords == end) continue

            val distance = distances.getValue(current)
            getNeighbors(current)
                .filter { it.coords in this && it !in distances }
                .forEach { next ->
                    val weight = this[next.coords].digitToInt()
                    if (distance + weight < distances.getValue(next)) {
                        distances[next] = distance + weight
                        queue.add(next)
                    }
                }
        }

        return distances.filter { it.key.coords == end && validEnd(it.key) }.values.min()
    }
}


fun main() = Day17().run()
