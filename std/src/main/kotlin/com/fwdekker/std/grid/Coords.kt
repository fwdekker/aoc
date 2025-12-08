@file:Suppress("unused")

package com.fwdekker.std.grid


/**
 * Two-dimensional coordinates.
 */
typealias Coords = Pair<Int, Int>

/**
 * Utility "constructor" for creating [Coords] from integers.
 */
fun Coords(row: Number, col: Number): Coords = Pair(row.toInt(), col.toInt())

/**
 * The vertical coordinate.
 */
val Coords.row: Int get() = first

/**
 * The horizontal coordinate.
 */
val Coords.col: Int get() = second

/**
 * Returns the coordinates directly [North] of `this`.
 */
fun Coords.north(distance: Int = 1): Coords = move(rows = -distance)

/**
 * Returns the coordinates directly [North] and then [East] of `this`.
 */
fun Coords.northEast(distance: Int = 1): Coords = move(rows = -distance, cols = distance)

/**
 * Returns the coordinates directly [South] of `this`.
 */
fun Coords.east(distance: Int = 1): Coords = move(cols = distance)

/**
 * Returns the coordinates directly [South] and then [East] of `this`.
 */
fun Coords.southEast(distance: Int = 1): Coords = move(rows = distance, cols = distance)

/**
 * Returns the coordinates directly [East] of `this`.
 */
fun Coords.south(distance: Int = 1): Coords = move(rows = distance)

/**
 * Returns the coordinates directly [South] and then [West] of `this`.
 */
fun Coords.southWest(distance: Int = 1): Coords = move(rows = distance, cols = -distance)

/**
 * Returns the coordinates directly [West] of `this`.
 */
fun Coords.west(distance: Int = 1): Coords = move(cols = -distance)

/**
 * Returns the coordinates directly [North] and then [West] of `this`.
 */
fun Coords.northWest(distance: Int = 1): Coords = move(rows = -distance, cols = -distance)

/**
 * Returns the coordinates located [rows] to the south and [cols] to the east.
 */
fun Coords.move(rows: Int = 0, cols: Int = 0): Coords = Coords(row + rows, col + cols)

/**
 * Returns the coordinates in position [direction] relative to `this`.
 */
fun Coords.move(direction: Direction, distance: Int = 1): Coords =
    when (direction) {
        North -> north(distance)
        East -> east(distance)
        South -> south(distance)
        West -> west(distance)
        NorthEast -> northEast(distance)
        SouthWest -> southWest(distance)
        SouthEast -> southEast(distance)
        NorthWest -> northWest(distance)
    }

/**
 * Keeps making a [move] in [direction] until [condition] no longer holds. The starting point is included, and the
 * [condition] is checked on the starting point.
 */
fun Coords.trail(direction: Direction, condition: (Coords) -> Boolean): Sequence<Coords> {
    var current = this
    return sequence {
        while (condition(current)) {
            yield(current)
            current = current.move(direction)
        }
    }
}

/**
 * Returns the neighboring coordinates in all four cardinal [Direction]s.
 */
val Coords.cardinals: List<Coords> get() = Cardinal.entries.map { move(it) }

/**
 * Returns the neighboring coordinates in all four ordinal [Direction]s.
 */
val Coords.ordinals: List<Coords> get() = Ordinal.entries.map { move(it) }

/**
 * Returns the neighboring coordinates in all four cardinal and all four ordinal [Direction]s.
 */
val Coords.principals: List<Coords> get() = Direction.entries.map { move(it) }

/**
 * Creates a total ordering of all [Coords]s, comparing first the [row] and then the [col].
 */
operator fun Coords.compareTo(other: Coords): Int {
    val row = this.row.compareTo(other.row)
    if (row != 0) return row
    return this.col.compareTo(other.col)
}


/**
 * Three-dimensional coordinates.
 */
typealias Coords3 = Triple<Long, Long, Long>

/**
 * Utility "constructor" for creating [Coords3] from integers.
 */
fun Coords3(x: Number, y: Number, z: Number): Coords3 = Triple(x.toLong(), y.toLong(), z.toLong())

/**
 * The x coordinate.
 */
val Coords3.x: Long get() = first

/**
 * The y coordinate.
 */
val Coords3.y: Long get() = second

/**
 * The z coordinate.
 */
val Coords3.z: Long get() = third

/**
 * Creates a total ordering of all [Coords3]s, comparing first the [x], then the [y], and finally the [z].
 */
operator fun Coords3.compareTo(other: Coords3): Int {
    val x = this.x.compareTo(other.x)
    if (x != 0) return x
    val y = this.y.compareTo(other.y)
    if (y != 0) return y
    return this.z.compareTo(other.z)
}
