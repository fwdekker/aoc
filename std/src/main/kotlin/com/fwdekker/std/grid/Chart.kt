package com.fwdekker.std.grid

import com.fwdekker.std.linesNotBlank
import com.fwdekker.std.sections


/**
 * A 2-dimensional chart (or map), represented by a list of strings, all with the same length.
 *
 * @see Grid
 */
typealias Chart = Grid<Char>

/**
 * Converts this (multi-line) string into a [Chart].
 */
fun String.toChart(): Chart = linesNotBlank().map { it.toList() }

/**
 * Converts each of the [sections] of this multi-line string into a [Chart].
 */
fun String.toCharts(): List<Chart> = sections().map { section -> section.map { it.toList() } }

/**
 * Pretty-prints the chart's contents.
 */
fun Chart.print(): String = joinToString(separator = "\n") { it.joinToString("") }
