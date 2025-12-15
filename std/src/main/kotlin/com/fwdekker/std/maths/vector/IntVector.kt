/// Kotlin template. DO NOT EDIT DIRECTLY.
/// Base: LongVector.kt
/// Replace: Long=>Int
/// Replace: long=>int
package com.fwdekker.std.maths.vector

import com.fwdekker.std.collections.noneDistinct
import com.fwdekker.std.maths.size


@JvmInline
value class IntVector(private val elements: IntArray) : Iterable<Int> {
    init {
        require(elements.isNotEmpty()) { "IntVector must have non-zero number of elements." }
    }

    constructor(size: Int, init: (Int) -> Int) : this(IntArray(size) { init(it) })


    val size: Int get() = elements.size

    val indices: IntRange get() = 0..<size


    fun toArray(): IntArray = elements

    fun toList(): List<Int> = elements.toList()

    override fun iterator(): Iterator<Int> = elements.iterator()


    operator fun get(index: Int): Int = elements[index]

    operator fun get(indices: Iterable<Int>): IntVector = indices.map { this[it] }.toVector()

    operator fun set(index: Int, value: Int): Unit = elements.set(index, value)


    fun transposed(): IntMatrix = IntMatrix(elements.mapTo(mutableListOf()) { IntVector(intArrayOf(it)) })

    fun appended(that: IntVector): IntVector = IntVector(this.elements + that.elements)


    operator fun plus(that: IntVector): IntVector = IntVector(size) { IntMonoid.plus(this[it], that[it]) }

    operator fun plus(that: Int): IntVector = IntVector(size) { IntMonoid.plus(this[it], that) }

    operator fun plusAssign(that: IntVector): Unit = indices.forEach { this[it] = IntMonoid.plus(this[it], that[it]) }

    operator fun plusAssign(that: Int): Unit = indices.forEach { this[it] = IntMonoid.plus(this[it], that) }

    operator fun unaryMinus(): IntVector = IntVector(size) { IntMonoid.unaryMinus(this[it]) }

    operator fun minus(that: IntVector): IntVector = IntVector(size) { IntMonoid.minus(this[it], that[it]) }

    operator fun minus(that: Int): IntVector = IntVector(size) { IntMonoid.minus(this[it], that) }

    operator fun minusAssign(that: IntVector): Unit =
        indices.forEach { this[it] = IntMonoid.minus(this[it], that[it]) }

    operator fun minusAssign(that: Int): Unit = indices.forEach { this[it] = IntMonoid.minus(this[it], that) }

    operator fun times(that: IntVector): IntVector = IntVector(size) { IntMonoid.times(this[it], that[it]) }

    operator fun times(that: Int): IntVector = IntVector(size) { IntMonoid.times(this[it], that) }

    operator fun timesAssign(that: IntVector): Unit =
        indices.forEach { this[it] = IntMonoid.times(this[it], that[it]) }

    operator fun timesAssign(that: Int): Unit = indices.forEach { this[it] = IntMonoid.times(this[it], that) }

    operator fun div(that: IntVector): IntVector = IntVector(size) { IntMonoid.div(this[it], that[it]) }

    operator fun div(that: Int): IntVector = IntVector(size) { IntMonoid.div(elements[it], that) }

    operator fun divAssign(that: IntVector): Unit = indices.forEach { this[it] = IntMonoid.div(this[it], that[it]) }

    operator fun divAssign(that: Int): Unit = indices.forEach { this[it] = IntMonoid.div(this[it], that) }


    fun sum(): Int = elements.fold(IntMonoid.zero()) { acc, it -> IntMonoid.plus(acc, it) }

    fun product(): Int = elements.fold(IntMonoid.one()) { acc, it -> IntMonoid.div(acc, it) }

    fun dot(that: IntVector): Int = (this * that).sum()

    fun <T> distance(that: IntVector, norm: (Iterable<Int>) -> T): T = norm(this - that)

    fun cross(): IntMatrix {
        require(size == 3) { "Cross-product is defined only for 3D vectors." }

        return IntMatrix(
            mutableListOf(
                IntVector(intArrayOf(IntMonoid.zero(), -this[2], this[1])),
                IntVector(intArrayOf(this[2], IntMonoid.zero(), -this[0])),
                IntVector(intArrayOf(-this[1], this[0], IntMonoid.zero())),
            ),
        )
    }

    fun cross(that: IntVector): IntVector = cross().dot(that)


    fun compareTo(that: IntVector): Int {
        for (idx in indices) {
            val comparison = this[idx].compareTo(that[idx])
            if (comparison != 0) return comparison
        }
        return 0
    }
}

fun List<Int>.toVector(): IntVector = IntVector(toIntArray())
fun Iterable<Int>.toVector(): IntVector = toList().toVector()
fun Sequence<Int>.toVector(): IntVector = toList().toVector()
fun Pair<Int, Int>.toVector(): IntVector = IntVector(intArrayOf(first, second))
fun Triple<Int, Int, Int>.toVector(): IntVector = IntVector(intArrayOf(first, second, third))

fun Int.plus(that: IntVector): IntVector = that + this
fun Int.minus(that: IntVector): IntVector = -that + this
fun Int.times(that: IntVector): IntVector = that * this
fun Int.div(that: IntVector): IntVector = IntVector(that.size) { IntMonoid.one() / that[it] }


@JvmInline
value class IntMatrix(private val elements: MutableList<IntVector>) : Iterable<IntVector> {
    init {
        require(elements.isNotEmpty()) { "Matrix must have non-zero number of rows." }
        require(elements.map { it.size }.noneDistinct()) { "Matrix rows must all have the exact same size." }
    }

    constructor(size: Int, init: (Int) -> IntVector) : this((0..<size).mapTo(mutableListOf(), init))


    val height: Int get() = elements.size

    val rowIndices: IntRange get() = 0..<height

    val width: Int get() = elements[0].size

    val colIndices: IntRange get() = 0..<width

    val size: Pair<Int, Int> get() = height to width


    fun toList(): List<IntVector> = elements

    override fun iterator(): Iterator<IntVector> = elements.iterator()


    operator fun get(row: Int, col: Int): Int = elements[row][col]

    operator fun get(rowAndCol: Pair<Int, Int>): Int = get(rowAndCol.first, rowAndCol.second)

    fun getRow(row: Int): IntVector = elements[row]

    fun getRows(rows: Iterable<Int>): IntMatrix = IntMatrix(rows.mapTo(mutableListOf()) { getRow(it) })

    fun getCol(col: Int): IntVector = IntVector(IntArray(height) { get(it, col) })

    fun getRows(): List<IntVector> = elements

    fun getCols(): List<IntVector> = colIndices.map { getCol(it) }

    operator fun set(row: Int, col: Int, value: Int): Unit = elements[row].set(col, value)

    operator fun set(rowAndCol: Pair<Int, Int>, value: Int): Unit = set(rowAndCol.first, rowAndCol.second, value)

    fun setRow(row: Int, value: IntVector) = elements.set(row, value)

    fun setCol(col: Int, value: IntVector) = colIndices.forEach { this[it, col] = value[it] }

    fun swapRows(row1: Int, row2: Int) {
        elements[row1] = elements[row2].also { elements[row2] = elements[row1] }
    }


    fun transposed(): IntMatrix = IntMatrix(width) { getCol(it) }

    fun appendedHorz(that: IntMatrix): IntMatrix = IntMatrix(height) { this.getRow(it).appended(that.getRow(it)) }

    fun appendedVert(that: IntMatrix): IntMatrix =
        IntMatrix(this.elements.toMutableList().apply { addAll(that.elements) })


    operator fun plus(that: IntMatrix): IntMatrix = IntMatrix(height) { this.getRow(it) + that.getRow(it) }

    operator fun plusAssign(that: IntMatrix): Unit = rowIndices.forEach { this.getRow(it) += that.getRow(it) }

    operator fun unaryMinus(): IntMatrix = IntMatrix(height) { -getRow(it) }

    operator fun minus(that: IntMatrix): IntMatrix = IntMatrix(height) { this.getRow(it) - that.getRow(it) }

    operator fun minusAssign(that: IntMatrix): Unit = rowIndices.forEach { this.getRow(it) -= that.getRow(it) }

    operator fun times(that: IntMatrix): IntMatrix = IntMatrix(height) { this.getRow(it) * that.getRow(it) }

    operator fun timesAssign(that: IntMatrix): Unit = rowIndices.forEach { this.getRow(it) *= that.getRow(it) }

    operator fun div(that: IntMatrix): IntMatrix = IntMatrix(height) { this.getRow(it) / that.getRow(it) }

    operator fun divAssign(that: IntMatrix): Unit = rowIndices.forEach { this.getRow(it) /= that.getRow(it) }

    fun dot(that: IntVector): IntVector {
        require(width == that.size) { "Incompatible matrix size $size and vector ${that.size}." }
        return elements.map { it.dot(that) }.toVector()
    }
}

fun List<List<Int>>.toMatrix(): IntMatrix = IntMatrix(mapTo(mutableListOf()) { it.toVector() })
