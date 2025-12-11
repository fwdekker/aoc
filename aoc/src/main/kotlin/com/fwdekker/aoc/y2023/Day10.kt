package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.grid.Cardinal
import com.fwdekker.std.grid.Chart
import com.fwdekker.std.grid.Coords
import com.fwdekker.std.grid.Direction
import com.fwdekker.std.grid.East
import com.fwdekker.std.grid.Heading
import com.fwdekker.std.grid.North
import com.fwdekker.std.grid.South
import com.fwdekker.std.grid.West
import com.fwdekker.std.grid.cardinals
import com.fwdekker.std.grid.contains
import com.fwdekker.std.grid.coordsOf
import com.fwdekker.std.grid.get
import com.fwdekker.std.grid.move
import com.fwdekker.std.grid.toChart
import com.fwdekker.std.read


// See https://adventofcode.com/2023/day/10
class Day10(sample: Int? = null) : Day(year = 2023, day = 10, sample = sample) {
    private val maze = input.toChart()

    private val start = maze.coordsOf('S')
    private val cycle = Cardinal.entries.asSequence()
        .filter { start.move(it) in maze }
        .map { Raccoon(maze, Heading(start, it)) }
        .onEach { it.scurry() }
        .first { it.isCycle() }


    override fun part1(): Int = cycle.distance / 2

    override fun part2(): Int = cycle.getEnclosedSpace()
}

private class Raccoon(val maze: Chart, heading: Heading) {
    private val path: MutableSet<Coords> = mutableSetOf(heading.coords)
    private var heading: Heading? = heading

    private val leftPipes: MutableSet<Coords> = mutableSetOf()
    private val rightPipes: MutableSet<Coords> = mutableSetOf()

    val distance: Int get() = path.size


    fun isCycle(): Boolean = heading?.coords == path.first() && distance > 1

    fun isDone(): Boolean = isCycle() || heading == null


    fun scurry() {
        while (!isDone())
            step()
    }

    private fun step() {
        val oldHeading = heading!!
        val newHeading = maze[oldHeading.coords].traverse(oldHeading)
            .also { heading = it }
            ?: return

        path.add(newHeading.coords)

        when (newHeading.direction) {
            oldHeading.direction.left -> {
                rightPipes.add(oldHeading.go { right }.coords)
                rightPipes.add(oldHeading.go { ahead }.coords)
            }

            oldHeading.direction.right -> {
                leftPipes.add(oldHeading.go { left }.coords)
                leftPipes.add(oldHeading.go { ahead }.coords)
            }

            oldHeading.direction.ahead -> {
                leftPipes.add(oldHeading.go { left }.coords)
                rightPipes.add(oldHeading.go { right }.coords)
            }

            else -> {}
        }
    }


    fun getEnclosedSpace(): Int {
        require(isDone()) { "Cannot find enclosed space before scurrying." }
        require(isCycle()) { "Cannot find enclosed space if no cycle was scurried around." }

        val encloses = (if (rightPipes.size > leftPipes.size) leftPipes else rightPipes).toMutableSet()

        encloses.removeAll(path)
        encloses.retainAll { it in maze }

        var enclosed = 0
        while (encloses.size > enclosed) {
            enclosed = encloses.size
            encloses.addAll(encloses.flatMap { it.cardinals }.toSet().subtract(path))
            encloses.retainAll { it in maze }
        }

        return encloses.size
    }


    private fun Char.traverse(from: Heading): Heading? {
        val table: Map<Char, Direction.() -> Direction> =
            when (from.direction) {
                North -> mapOf('S' to { ahead }, '|' to { ahead }, '7' to { left }, 'F' to { right })
                East -> mapOf('S' to { ahead }, '-' to { ahead }, 'J' to { left }, '7' to { right })
                South -> mapOf('S' to { ahead }, '|' to { ahead }, 'J' to { right }, 'L' to { left })
                West -> mapOf('S' to { ahead }, '-' to { ahead }, 'L' to { right }, 'F' to { left })
                else -> error("Ordinal directions should not exist here.")
            }

        return table[this]?.invoke(from.direction)?.let(from::go)
    }
}


fun main() = Day10().run()
