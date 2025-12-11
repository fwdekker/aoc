package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.NeverNullMap
import com.fwdekker.std.collections.neverNull
import com.fwdekker.std.foldSelfIndexed
import com.fwdekker.std.maths.cartesian


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
    ).mapValues { (_, values) -> values.mapValues { (_, value) -> value.map { it + 'A' } }.neverNull() }.neverNull()
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
    ).mapValues { (_, values) -> values.mapValues { (_, value) -> value.map { it + 'A' } }.neverNull() }.neverNull()

    private val codeToKeysCache = mutableMapOf<String, List<String>>()


    // TODO: Improve memory efficiency
    override fun part1() = calculateComplexity(2)

    // TODO: Implement this; current implementation does not finish
    override fun part2() = calculateComplexity(25)


    private fun codeToKeys(pad: NeverNullMap<Char, NeverNullMap<Char, List<String>>>, code: String): List<String> =
        codeToKeysCache.getOrPut(code) {
            "A$code"
                .zipWithNext { from, to -> pad[from][to] }
                .reduce { acc, paths -> acc.cartesian(paths).map { it.first + it.second }.toList() }
        }

    private fun calculateComplexity(robotsInBetween: Int) =
        codes
            .sumOf { code ->
                listOf(code)
                    .foldSelfIndexed(robotsInBetween + 1) { idx, acc ->
                        acc.flatMap { codeToKeys(if (idx == 0) numpad else keypad, it) }.allMinOf { it.length }
                    }
                    .minOf { it.length }
                    .let { code.dropLast(1).toLong() * it }
            }

    private fun <T, R : Comparable<R>> List<T>.allMinOf(selector: (T) -> R): List<T> =
        minOf(selector).let { min -> filter { selector(it) == min } }
}


fun main() = Day21().run()
