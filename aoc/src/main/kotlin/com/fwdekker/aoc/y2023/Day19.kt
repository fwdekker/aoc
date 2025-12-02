package com.fwdekker.aoc.y2023

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.appendingFold
import com.fwdekker.std.collections.map
import com.fwdekker.std.maths.productOf
import com.fwdekker.std.maths.splitGEQ
import com.fwdekker.std.maths.splitLEQ
import com.fwdekker.std.read
import com.fwdekker.std.sections


// See https://adventofcode.com/2023/day/19
class Day19(sample: Int? = null) : Day(year = 2023, day = 19, sample = sample) {
    private val lineSets = read(resource).sections()
    private val workflows = lineSets[0].map { Workflow.fromString(it) }.associateBy { it.name }
    private val parts = lineSets[1].map { Part.fromString(it) }


    override fun part1(): Int =
        workflows.filter(parts).sumOf { it.value() }

    override fun part2(): Long =
        workflows.filter(listOf(Part("xmas".associateWith { 1..4000 }))).sumOf { it.combos() }


    private fun Map<String, Workflow>.filter(parts: List<Part>, start: String = "in"): List<Part> =
        parts.map { Pair(it, start) }.toMutableList()
            .appendingFold(listOf()) { queue, accepted, (part, workflow) ->
                getValue(workflow).process(part)
                    .filter { it.second != "R" }
                    .partition { it.second == "A" }
                    .also { (_, forwarded) -> queue.addAll(forwarded) }
                    .let { (newlyAccepted, _) -> accepted + newlyAccepted.map { it.first } }
            }


    private class Part(val properties: Map<Char, IntRange>) : Map<Char, IntRange> by properties {
        fun value(): Int = values.sumOf { it.sum() }

        fun combos(): Long = values.productOf { it.count() }


        companion object {
            fun fromString(string: String): Part =
                string
                    .removeSurrounding("{", "}")
                    .split(',')
                    .map { it.split('=') }
                    .associate { it[0][0] to it[1].toInt() }
                    .mapValues { (_, it) -> it..it }
                    .let { Part(it) }
        }
    }

    private class Rule(val filter: (Part) -> Pair<Part, Part>, val target: String) {
        companion object {
            fun fromString(string: String): Rule {
                if (':' !in string)
                    return Rule({ it to Part(emptyMap()) }, string)

                val (filter, target) = string.split(':')
                val (category, threshold) = filter.split('<', '>').let { Pair(it[0][0], it[1].toInt()) }

                return Rule(
                    { part ->
                        part.getValue(category)
                            .let { if (filter[1] == '<') it.splitLEQ(threshold) else it.splitGEQ(threshold) }
                            .map { Part(part + (category to it)) }
                    },
                    target
                )
            }
        }
    }

    private class Workflow(val name: String, val rules: List<Rule>, val fallback: String) {
        fun process(part: Part): List<Pair<Part, String>> =
            rules
                .fold(Pair(emptyList<Pair<Part, String>>(), part)) { (forwarded, part), rule ->
                    val (included, excluded) = rule.filter(part)
                    Pair(forwarded + Pair(included, rule.target), excluded)
                }
                .let { (forwarded, rejected) -> forwarded + Pair(rejected, fallback) }
                .filter { it.first.combos() > 0L }


        companion object {
            fun fromString(string: String): Workflow =
                Workflow(
                    name = string.takeWhile { it != '{' },
                    rules = string
                        .dropWhile { it != '{' }
                        .removeSurrounding("{", "}")
                        .split(',')
                        .dropLast(1)
                        .map { Rule.fromString(it) },
                    fallback = string.takeLastWhile { it != ',' }.dropLast(1),
                )
        }
    }
}


fun main() = Day19().run()
