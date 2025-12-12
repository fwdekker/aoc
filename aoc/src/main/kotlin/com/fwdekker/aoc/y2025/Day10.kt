package com.fwdekker.aoc.y2025

import com.fwdekker.aoc.Day
import com.fwdekker.std.allInts
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.maths.powerSet
import com.fwdekker.std.splitAtIndex
import com.microsoft.z3.Context
import com.microsoft.z3.IntNum
import com.microsoft.z3.Status


// See https://adventofcode.com/2025/day/10
class Day10(sample: Int? = null) : Day(year = 2025, day = 10, sample = sample) {
    private val machines = input.linesNotBlank()
        .map { abc ->
            abc.splitAtIndex(abc.indexOf(']') + 1).let { (a, bc) ->
                bc.splitAtIndex(bc.indexOf('{') - 1).let { (b, c) ->
                    Triple(
                        a.trim().removeSurrounding("[", "]").map { it == '#' },
                        b.trim().split(' ').map { it.allInts().toSet() },
                        c.trim().removeSurrounding("{", "}").allInts(),
                    )
                }
            }
        }


    override fun part1(): Int =
        machines.sumOf { (lights, buttons, _) ->
            buttons.powerSet(minSize = 1)
                .map { selection ->
                    selection.fold(Array(lights.size) { false }) { state, button ->
                        button.forEach { state[it] = !state[it] }
                        state
                    } to selection.size
                }
                .filter { (state, _) -> state.withIndex().all { (idx, it) -> it == lights[idx] } }
                .minOf { (_, pressed) -> pressed }
        }

    override fun part2(): Int =
        machines.sumOf { (_, buttons, joltage) ->
            val ctx = Context()
            val opt = ctx.mkOptimize()

            val pressesVar = ctx.mkIntConst("presses")
            val buttonVars = buttons.indices.map { ctx.mkIntConst("button$it") }.toTypedArray()
            val joltageVars = joltage.indices.associateWith { joltageIdx ->
                buttonVars.filterIndexed { idx, _ -> buttons[idx].contains(joltageIdx) }.toTypedArray()
            }

            // Define joltage to equal number of corresponding button presses
            joltageVars.forEach { (idx, lights) -> opt.Add(ctx.mkEq(ctx.mkInt(joltage[idx]), ctx.mkAdd(*lights))) }

            // Define total presses to equal total number of presses
            opt.Add(ctx.mkEq(pressesVar, ctx.mkAdd(*buttonVars)))

            // Require non-negative button press counts
            val zero = ctx.mkInt(0)
            buttonVars.forEach { opt.Add(ctx.mkGe(it, zero)) }

            // Solve
            opt.MkMinimize(pressesVar)
            require(opt.Check() === Status.SATISFIABLE) { "Unsatisfiable system." }
            (opt.model.evaluate(pressesVar, false) as IntNum).int
        }
}


fun main() = Day10().run()
