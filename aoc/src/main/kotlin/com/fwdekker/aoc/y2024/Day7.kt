package com.fwdekker.aoc.y2024

import com.fwdekker.aoc.Day
import com.fwdekker.std.collections.firsts
import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.maths.orderedPartitions
import com.fwdekker.std.maths.sum
import com.fwdekker.std.read
import com.fwdekker.std.toBigIntegers
import java.math.BigInteger


// See https://adventofcode.com/2024/day/7
class Day7(sample: Int? = null) : Day(year = 2024, day = 7, sample = sample) {
    private val equations =
        read(resource).linesNotBlank().map { it.split(':') }.map { it[0].toBigInteger() to it[1].toBigIntegers(' ') }


    override fun part1() = calibrate(operatorCount = 2)

    override fun part2() = calibrate(operatorCount = 3)


    private fun calibrate(operatorCount: Int) =
        equations
            .filter { (total, operands) ->
                (0..operands.size - 2).toList().orderedPartitions(operatorCount)
                    .any { operators ->
                        operands[0] == operators.indices.fold(total) { acc, idx ->
                            val operand = operands[operands.lastIndex - idx]
                            when (operators[idx]) {
                                0 ->
                                    if (acc < operand) return@any false
                                    else acc - operand

                                1 ->
                                    if (acc % operand != BigInteger.ZERO) return@any false
                                    else acc / operand

                                else ->
                                    if (!acc.toString().endsWith(operand.toString())) return@any false
                                    else acc / BigInteger.TEN.pow(operand.toString().length)
                            }
                        }
                }
            }
            .firsts().sum()
}


fun main() = Day7().run()
