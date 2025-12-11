package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.grid.Direction
import com.fwdekker.std.grid.SouthEast
import com.fwdekker.std.grid.SouthWest
import com.fwdekker.std.grid.allCoords
import com.fwdekker.std.grid.allCoordsOf
import com.fwdekker.std.grid.canMove
import com.fwdekker.std.grid.get
import com.fwdekker.std.grid.getLine
import com.fwdekker.std.grid.lastColIndex
import com.fwdekker.std.grid.lastRowIndex
import com.fwdekker.std.grid.northEast
import com.fwdekker.std.grid.northWest
import com.fwdekker.std.grid.toChart
import com.fwdekker.std.read


// See https://adventofcode.com/2024/day/4
class Day4(sample: Int? = null) : Day(year = 2024, day = 4, sample = sample) {
    private val puzzle = input.toChart()


    override fun part1(): Int =
        puzzle.allCoordsOf('X')
            .sumOf { coords ->
                Direction.entries
                    .filter { puzzle.canMove(coords, it, 3) }
                    .map { puzzle.getLine(coords, it, 3) }
                    .count { line -> line.joinToString("") == "XMAS" }
            }

    override fun part2(): Int {
        return puzzle.allCoords
            .filter { (x, y) -> x != 0 && x != puzzle.lastRowIndex && y != 0 && y != puzzle.lastColIndex }
            .filter { coords -> puzzle[coords] == 'A' }
            .count { coords ->
                listOf(
                    puzzle.getLine(coords.northWest(), SouthEast, 2).joinToString(""),
                    puzzle.getLine(coords.northEast(), SouthWest, 2).joinToString("")
                ).all { it == "MAS" || it == "SAM" }
            }
    }
}


fun main() = Day4().run()
