package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.AreaChartGraph
import com.fwdekker.aoc.Day
import com.fwdekker.std.grid.Cardinal
import com.fwdekker.std.grid.allCoords
import com.fwdekker.std.grid.move
import com.fwdekker.std.grid.toChart
import com.fwdekker.std.grid.trail


// See https://adventofcode.com/2024/day/12
class Day12(sample: Int? = null) : Day(year = 2024, day = 12, sample = sample) {
    private val garden = input.toChart()
    private val graph = AreaChartGraph(garden)


    override fun part1() =
        garden.allCoords
            .map { graph.depthFirst(it).toSet() }
            .distinct()
            .sumOf { region -> region.size * region.sumOf { 4 - graph.getNeighbours(it).size } }

    override fun part2() =
        garden.allCoords
            .map { graph.depthFirst(it).toSet() }
            .distinct()
            .sumOf { region ->
                region.size * region.flatMap { spot ->
                    Cardinal.entries
                        .filter { spot.move(it) !in region }
                        .map { direction ->
                            val outside = spot.move(direction)
                            val edge = setOf(outside) +
                                outside.trail(direction.left) { it !in region && it.move(direction.behind) in region } +
                                outside.trail(direction.right) { it !in region && it.move(direction.behind) in region }

                            edge to direction
                        }
                }.distinct().size
            }
}


fun main() = Day12().run()
