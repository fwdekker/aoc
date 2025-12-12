package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.neverNull
import com.fwdekker.std.memoised


// See https://adventofcode.com/2024/day/21
class Day21(sample: Int? = null) : Day(year = 2024, day = 21, sample = sample) {
    private val codes = input.lines()
    private val numpad = mapOf(
        'A' to mapOf(
            'A' to listOf(""),
            '0' to listOf("<"),
            '1' to listOf("^<<", "<^<"),
            '2' to listOf("^<", "<^"),
            '3' to listOf("^"),
            '4' to listOf("^^<<", "^<^<", "^<<^", "<^^<", "<^<^"),
            '5' to listOf("^^<", "^<^", "<^^"),
            '6' to listOf("^^"),
            '7' to listOf("^^^<<", "^^<^<", "^^<<^", "^<^^<", "^<^<^", "^<<^^", "<^^^<", "<^^<^", "<^<^^"),
            '8' to listOf("^^^<", "^^<^", "^<^^", "<^^^"),
            '9' to listOf("^^^"),
        ),
        '0' to mapOf(
            'A' to listOf(">"),
            '0' to listOf(""),
            '1' to listOf("^<"),
            '2' to listOf("^"),
            '3' to listOf("^>", ">^"),
            '4' to listOf("^^<", "^<^"),
            '5' to listOf("^^"),
            '6' to listOf("^^>", "^>^", ">^^"),
            '7' to listOf("^^^<", "^^<^", "^<^^"),
            '8' to listOf("^^^"),
            '9' to listOf("^^^>", "^^>^", "^>^^", ">^^^"),
        ),
        '1' to mapOf(
            'A' to listOf(">>v", ">v>"),
            '0' to listOf(">v"),
            '1' to listOf(""),
            '2' to listOf(">"),
            '3' to listOf(">>"),
            '4' to listOf("^"),
            '5' to listOf(">^", "^>"),
            '6' to listOf(">>^", ">^>", "^>>"),
            '7' to listOf("^^"),
            '8' to listOf("^^>", "^>^", ">^^"),
            '9' to listOf("^^>>", "^>^>", "^>>^", ">^^>", ">^>^", ">>^^"),
        ),
        '2' to mapOf(
            'A' to listOf("v>", ">v"),
            '0' to listOf("v"),
            '1' to listOf("<"),
            '2' to listOf(""),
            '3' to listOf(">"),
            '4' to listOf("^<", "<^"),
            '5' to listOf("^"),
            '6' to listOf("^>", ">^"),
            '7' to listOf("^^<", "^<^", "<^^"),
            '8' to listOf("^^"),
            '9' to listOf("^^>", "^>^", ">^^"),
        ),
        '3' to mapOf(
            'A' to listOf("v"),
            '0' to listOf("v<", "<v"),
            '1' to listOf("<<"),
            '2' to listOf("<"),
            '3' to listOf(""),
            '4' to listOf("^<<", "<^<", "<<^"),
            '5' to listOf("^<", "<^"),
            '6' to listOf("^"),
            '7' to listOf("^^<<", "^<^<", "^<<^", "<^^<", "<^<^", "<<^^"),
            '8' to listOf("^^<", "^<^", "<^^"),
            '9' to listOf("^^"),
        ),
        '4' to mapOf(
            'A' to listOf("v>v>", "v>>v", ">vv>", ">v>v", ">>vv"),
            '0' to listOf("v>v", ">vv"),
            '1' to listOf("v"),
            '2' to listOf("v>", ">v"),
            '3' to listOf("v>>", ">v>", ">>v"),
            '4' to listOf(""),
            '5' to listOf(">"),
            '6' to listOf(">>"),
            '7' to listOf("^"),
            '8' to listOf("^>", ">^"),
            '9' to listOf("^>>", ">^>", ">>^"),
        ),
        '5' to mapOf(
            'A' to listOf("vv>", "v>v", ">vv"),
            '0' to listOf("vv"),
            '1' to listOf("v<", "<v"),
            '2' to listOf("v"),
            '3' to listOf("v>", ">v"),
            '4' to listOf("<"),
            '5' to listOf(""),
            '6' to listOf(">"),
            '7' to listOf("^<", "<^"),
            '8' to listOf("^"),
            '9' to listOf("^>", ">^"),
        ),
        '6' to mapOf(
            'A' to listOf("vv"),
            '0' to listOf("vv<", "v<v", "<vv"),
            '1' to listOf("v<<", "<v<", "<<v"),
            '2' to listOf("v<", "<v"),
            '3' to listOf("v"),
            '4' to listOf("<<"),
            '5' to listOf("<"),
            '6' to listOf(""),
            '7' to listOf("^<<", "<^<", "<<^"),
            '8' to listOf("^<", "<^"),
            '9' to listOf("^"),
        ),
        '7' to mapOf(
            'A' to listOf("vv>v>", "vv>>v", "v>vv>", "v>v>v", "v>>vv", ">vvv>", ">vv>v", ">v>vv", ">>vvv"),
            '0' to listOf("vv>v", "v>vv", ">vvv"),
            '1' to listOf("vv"),
            '2' to listOf("vv>", "v>v", ">vv"),
            '3' to listOf("vv>>", "v>v>", "v>>v", ">vv>", ">v>v", ">>vv"),
            '4' to listOf("v"),
            '5' to listOf("v>", ">v"),
            '6' to listOf("v>>", ">v>", ">>v"),
            '7' to listOf(""),
            '8' to listOf(">"),
            '9' to listOf(">>"),
        ),
        '8' to mapOf(
            'A' to listOf("vvv>", "vv>v", "v>vv", ">vvv"),
            '0' to listOf("vvv"),
            '1' to listOf("vv<", "v<v", "<vv"),
            '2' to listOf("vv"),
            '3' to listOf("vv>", "v>v", ">vv"),
            '4' to listOf("v<", "<v"),
            '5' to listOf("v"),
            '6' to listOf("v>", ">v"),
            '7' to listOf("<"),
            '8' to listOf(""),
            '9' to listOf(">"),
        ),
        '9' to mapOf(
            'A' to listOf("vvv"),
            '0' to listOf("vvv<", "vv<v", "v<vv", "<vvv"),
            '1' to listOf("vv<<", "v<v<", "v<<v", "<vv<", "<v<v", "<<vv"),
            '2' to listOf("vv<", "v<v", "<vv"),
            '3' to listOf("vv"),
            '4' to listOf("v<<", "<v<", "<<v"),
            '5' to listOf("v<", "<v"),
            '6' to listOf("v"),
            '7' to listOf("<<"),
            '8' to listOf("<"),
            '9' to listOf(""),
        ),
    ).mapValues { (_, targets) -> targets.mapValues { (_, path) -> path.map { it + 'A' } }.neverNull() }.neverNull()
    private val keypad = mapOf(
        'A' to mapOf(
            'A' to listOf(""),
            '^' to listOf("<"),
            '<' to listOf("v<<", "<v<"),
            'v' to listOf("v<", "<v"),
            '>' to listOf("v"),
        ),
        '^' to mapOf(
            'A' to listOf(">"),
            '^' to listOf(""),
            '<' to listOf("v<"),
            'v' to listOf("v"),
            '>' to listOf("v>", ">v"),
        ),
        '<' to mapOf(
            'A' to listOf(">^>", ">>^"),
            '^' to listOf(">^"),
            '<' to listOf(""),
            'v' to listOf(">"),
            '>' to listOf(">>"),
        ),
        'v' to mapOf(
            'A' to listOf("^>", ">^"),
            '^' to listOf("^"),
            '<' to listOf("<"),
            'v' to listOf(""),
            '>' to listOf(">"),
        ),
        '>' to mapOf(
            'A' to listOf("^"),
            '^' to listOf("^<", "<^"),
            '<' to listOf("<<"),
            'v' to listOf("<"),
            '>' to listOf(""),
        ),
    ).mapValues { (_, targets) -> targets.mapValues { (_, path) -> path.map { it + 'A' } }.neverNull() }.neverNull()


    override fun part1() = calculateComplexity(2)

    override fun part2() = calculateComplexity(25)


    private val getPathLength = memoised { (path, robotsCurrent, robotsMax): Triple<String, Int, Int> ->
        val pad = if (robotsCurrent == robotsMax) numpad else keypad

        if (robotsCurrent < 0) path.length.toLong()
        else "A$path"
            .zipWithNext()
            .sumOf { (from, to) -> pad[from][to].minOf { callRecursive(Triple(it, robotsCurrent - 1, robotsMax)) } }
    }

    private fun calculateComplexity(robotsInBetween: Int): Long =
        codes.sumOf { getPathLength(Triple(it, robotsInBetween, robotsInBetween)) * it.dropLast(1).toLong() }
}


fun main() = Day21().run()
