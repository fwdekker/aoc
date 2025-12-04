@file:Suppress("unused")
package com.fwdekker.std.grid

import com.fwdekker.std.collections.getMod
import com.fwdekker.std.runningFoldSelf


/**
 * A 2-dimensional grid, represented by a list of lists, all with the same length.
 *
 * If not all lines have the same length, use [padRows] to turn the rough, ragged array into one with four smooth sides.
 */
typealias Grid<T> = List<List<T>>

typealias MutableGrid<T> = List<MutableList<T>>

typealias Row<T> = List<T>

typealias Col<T> = List<T>


/**
 * Pads all rows of the [Grid] with [padding] to ensure they all have at least [cols] columns.
 *
 * Rows with more than [cols] entries are not affected.
 */
fun <T> Grid<T>.padRows(padding: T, cols: Int = maxOf { it.size }): Grid<T> =
    map { row ->
        val remaining = cols - row.size

        if (remaining <= 0) row
        else row + List(cols - row.size) { padding }
    }


/**
 * The number of rows.
 */
val <T> Grid<T>.height: Int get() = size

/**
 * The number of columns.
 */
val <T> Grid<T>.width: Int get() = this[0].size

/**
 * The range of row indices.
 */
val <T> Grid<T>.rows: IntRange get() = indices

/**
 * The range of column indices.
 */
val <T> Grid<T>.cols: IntRange get() = this[0].indices

/**
 * The index of the last row.
 */
val <T> Grid<T>.lastRowIndex: Int get() = lastIndex

/**
 * The index of the last column.
 */
val <T> Grid<T>.lastColIndex: Int get() = this[0].lastIndex

/**
 * All pairs of coordinates in the grid.
 */
val <T> Grid<T>.allCoords: Sequence<Coords> get() =
    rows.asSequence().flatMap { row -> cols.map { col -> Coords(row, col) } }


/**
 * Returns the [row]th row.
 */
fun <T> Grid<T>.row(row: Int): Row<T> = this[row]

/**
 * Returns the [col]th column.
 */
fun <T> Grid<T>.col(col: Int): Col<T> = map { it[col] }

/**
 * Returns the row in which [coords] is located.
 */
fun <T> Grid<T>.rowOf(coords: Coords): Row<T> = row(coords.row)

/**
 * Returns the column in which [coords] is located.
 */
fun <T> Grid<T>.colOf(coords: Coords): Col<T> = col(coords.col)

/**
 * Returns the first row.
 */
val <T> Grid<T>.firstRow: Row<T> get() = first()

/**
 * Returns the first column.
 */
val <T> Grid<T>.firstCol: Col<T> get() = col(0)

/**
 * Returns the last row.
 */
val <T> Grid<T>.lastRow: Row<T> get() = last()

/**
 * Returns the last column.
 */
val <T> Grid<T>.lastCol: Col<T> get() = col(lastColIndex)


/**
 * Returns `true` if and only if [coords] fall within bounds.
 */
operator fun <T> Grid<T>.contains(coords: Coords): Boolean = coords.row in rows && coords.col in cols

/**
 * Returns the cell at [coords].
 */
operator fun <T> Grid<T>.get(coords: Coords): T = this[coords.row][coords.col]

/**
 * Writes [value] into the cell at [coords].
 */
operator fun <T> MutableGrid<T>.set(coords: Coords, value: T) {
    this[coords.row][coords.col] = value
}

/**
 * Returns the cell at [coords], or `null` if there is no cell at those coordinates.
 */
fun <T> Grid<T>.getOrNull(coords: Coords): T? = getOrNull(coords.row)?.getOrNull(coords.col)

/**
 * Returns the cell at wrapped [coords], so `cellMod(Coords(-1, -1))` returns the bottom-right cell.
 */
fun <T> Grid<T>.getMod(coords: Coords): T = getMod(coords.row).getMod(coords.col)

/**
 * Returns the first [Coords] at which [cell] is found.
 */
fun <T> Grid<T>.coordsOf(cell: T): Coords =
    withIndex().asSequence().map { Coords(it.index, it.value.indexOf(cell)) }.first { it.col >= 0 }

/**
 * Returns all [Coords] at which [cell] is found.
 */
fun <T> Grid<T>.allCoordsOf(cell: T): Sequence<Coords> = allCoords.filter { this[it] == cell }

/**
 * Returns all [Coords] at which the value satisfies the [selector].
 */
fun <T> Grid<T>.allCoordsOf(selector: (T) -> Boolean): Sequence<Coords> = allCoords.filter { selector(this[it]) }


/**
 * Returns the neighboring [Coords] in all four cardinal [Direction]s that are within this [Grid].
 */
fun <T> Grid<T>.cardinals(coords: Coords): List<Coords> = coords.cardinals.filter { it in this }

/**
 * Returns the neighboring [Coords] in all four ordinal [Direction]s that are within this [Grid].
 */
fun <T> Grid<T>.ordinals(coords: Coords): List<Coords> = coords.principals.filter { it in this }

/**
 * Returns the neighboring [Coords] in all four cardinal and all four ordinal [Direction]s that are within this [Grid].
 */
fun <T> Grid<T>.principals(coords: Coords): List<Coords> = coords.principals.filter { it in this }


/**
 * Returns `true` if and only if you can move [distance] steps in the given [direction] from [coords] in [this].
 */
fun <T> Grid<T>.canMove(coords: Coords, direction: Direction, distance: Int = 1): Boolean =
    coords.move(direction, distance) in this

/**
 * @see canMove
 */
fun <T> Grid<T>.canMove(heading: Heading, distance: Int = 1): Boolean =
    canMove(heading.coords, heading.direction, distance)

/**
 * Returns all elements on the line piece from [coords] up to and including [coords] plus [distance] steps in the given
 * [direction].
 */
fun <T> Grid<T>.getLine(coords: Coords, direction: Direction, distance: Int = 1): Sequence<T> =
    coords.runningFoldSelf(distance) { it.move(direction) }.map { get(it) }

/**
 * @see getLine
 */
fun <T> Grid<T>.getLine(heading: Heading, distance: Int = 1): Sequence<T> =
    getLine(heading.coords, heading.direction, distance)


/**
 * Transposes, flipping semantics of rows and columns.
 */
fun <T> Grid<T>.transpose(): Grid<T> = cols.map(::col)

/**
 * Reverses the order of rows.
 */
fun <T> Grid<T>.flipUD(): Grid<T> = reversed()

/**
 * Reverses the order of columns.
 */
fun <T> Grid<T>.flipLR(): Grid<T> = map { it.reversed() }

/**
 * Rotates clockwise.
 */
fun <T> Grid<T>.rotateCW(): Grid<T> = transpose().flipLR()

/**
 * Rotates counter-clockwise.
 */
fun <T> Grid<T>.rotateCCW(): Grid<T> = transpose().flipUD()

/**
 * Rotates halfway around the clock.
 */
fun <T> Grid<T>.rotateHalf(): Grid<T> = flipLR().flipUD()


/**
 * Returns a mutable copy of this [Grid].
 */
fun <T> Grid<T>.mutable(): MutableGrid<T> = map { it.toMutableList() }
