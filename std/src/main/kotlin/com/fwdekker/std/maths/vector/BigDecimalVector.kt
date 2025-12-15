/// Kotlin template. DO NOT EDIT DIRECTLY.
/// Base: LongVector.kt
/// Replace: toLongArray=>toTypedArray
/// Replace: LongArray=>Array<BigDecimal>
/// Replace: Long=>BigDecimal
/// Replace: longArrayOf=>arrayOf
/// Import: java.math.BigDecimal
package com.fwdekker.std.maths.vector

import com.fwdekker.std.collections.noneDistinct
import com.fwdekker.std.maths.size
import java.math.BigDecimal


@JvmInline
value class BigDecimalVector(private val elements: Array<BigDecimal>) : Iterable<BigDecimal> {
    init {
        require(elements.isNotEmpty()) { "BigDecimalVector must have non-zero number of elements." }
    }

    constructor(size: Int, init: (Int) -> BigDecimal) : this(Array<BigDecimal>(size) { init(it) })


    val size: Int get() = elements.size

    val indices: IntRange get() = 0..<size


    fun toArray(): Array<BigDecimal> = elements

    fun toList(): List<BigDecimal> = elements.toList()

    override fun iterator(): Iterator<BigDecimal> = elements.iterator()


    operator fun get(index: Int): BigDecimal = elements[index]

    operator fun get(indices: Iterable<Int>): BigDecimalVector = indices.map { this[it] }.toVector()

    operator fun set(index: Int, value: BigDecimal): Unit = elements.set(index, value)


    fun transposed(): BigDecimalMatrix = BigDecimalMatrix(elements.mapTo(mutableListOf()) { BigDecimalVector(arrayOf(it)) })

    fun appended(that: BigDecimalVector): BigDecimalVector = BigDecimalVector(this.elements + that.elements)


    operator fun plus(that: BigDecimalVector): BigDecimalVector = BigDecimalVector(size) { BigDecimalMonoid.plus(this[it], that[it]) }

    operator fun plus(that: BigDecimal): BigDecimalVector = BigDecimalVector(size) { BigDecimalMonoid.plus(this[it], that) }

    operator fun plusAssign(that: BigDecimalVector): Unit = indices.forEach { this[it] = BigDecimalMonoid.plus(this[it], that[it]) }

    operator fun plusAssign(that: BigDecimal): Unit = indices.forEach { this[it] = BigDecimalMonoid.plus(this[it], that) }

    operator fun unaryMinus(): BigDecimalVector = BigDecimalVector(size) { BigDecimalMonoid.unaryMinus(this[it]) }

    operator fun minus(that: BigDecimalVector): BigDecimalVector = BigDecimalVector(size) { BigDecimalMonoid.minus(this[it], that[it]) }

    operator fun minus(that: BigDecimal): BigDecimalVector = BigDecimalVector(size) { BigDecimalMonoid.minus(this[it], that) }

    operator fun minusAssign(that: BigDecimalVector): Unit =
        indices.forEach { this[it] = BigDecimalMonoid.minus(this[it], that[it]) }

    operator fun minusAssign(that: BigDecimal): Unit = indices.forEach { this[it] = BigDecimalMonoid.minus(this[it], that) }

    operator fun times(that: BigDecimalVector): BigDecimalVector = BigDecimalVector(size) { BigDecimalMonoid.times(this[it], that[it]) }

    operator fun times(that: BigDecimal): BigDecimalVector = BigDecimalVector(size) { BigDecimalMonoid.times(this[it], that) }

    operator fun timesAssign(that: BigDecimalVector): Unit =
        indices.forEach { this[it] = BigDecimalMonoid.times(this[it], that[it]) }

    operator fun timesAssign(that: BigDecimal): Unit = indices.forEach { this[it] = BigDecimalMonoid.times(this[it], that) }

    operator fun div(that: BigDecimalVector): BigDecimalVector = BigDecimalVector(size) { BigDecimalMonoid.div(this[it], that[it]) }

    operator fun div(that: BigDecimal): BigDecimalVector = BigDecimalVector(size) { BigDecimalMonoid.div(elements[it], that) }

    operator fun divAssign(that: BigDecimalVector): Unit = indices.forEach { this[it] = BigDecimalMonoid.div(this[it], that[it]) }

    operator fun divAssign(that: BigDecimal): Unit = indices.forEach { this[it] = BigDecimalMonoid.div(this[it], that) }


    fun sum(): BigDecimal = elements.fold(BigDecimalMonoid.zero()) { acc, it -> BigDecimalMonoid.plus(acc, it) }

    fun product(): BigDecimal = elements.fold(BigDecimalMonoid.one()) { acc, it -> BigDecimalMonoid.div(acc, it) }

    fun dot(that: BigDecimalVector): BigDecimal = (this * that).sum()

    fun <T> distance(that: BigDecimalVector, norm: (Iterable<BigDecimal>) -> T): T = norm(this - that)

    fun cross(): BigDecimalMatrix {
        require(size == 3) { "Cross-product is defined only for 3D vectors." }

        return BigDecimalMatrix(
            mutableListOf(
                BigDecimalVector(arrayOf(BigDecimalMonoid.zero(), -this[2], this[1])),
                BigDecimalVector(arrayOf(this[2], BigDecimalMonoid.zero(), -this[0])),
                BigDecimalVector(arrayOf(-this[1], this[0], BigDecimalMonoid.zero())),
            ),
        )
    }

    fun cross(that: BigDecimalVector): BigDecimalVector = cross().dot(that)


    fun compareTo(that: BigDecimalVector): Int {
        for (idx in indices) {
            val comparison = this[idx].compareTo(that[idx])
            if (comparison != 0) return comparison
        }
        return 0
    }
}

fun List<BigDecimal>.toVector(): BigDecimalVector = BigDecimalVector(toTypedArray())
fun Iterable<BigDecimal>.toVector(): BigDecimalVector = toList().toVector()
fun Sequence<BigDecimal>.toVector(): BigDecimalVector = toList().toVector()
fun Pair<BigDecimal, BigDecimal>.toVector(): BigDecimalVector = BigDecimalVector(arrayOf(first, second))
fun Triple<BigDecimal, BigDecimal, BigDecimal>.toVector(): BigDecimalVector = BigDecimalVector(arrayOf(first, second, third))

fun BigDecimal.plus(that: BigDecimalVector): BigDecimalVector = that + this
fun BigDecimal.minus(that: BigDecimalVector): BigDecimalVector = -that + this
fun BigDecimal.times(that: BigDecimalVector): BigDecimalVector = that * this
fun BigDecimal.div(that: BigDecimalVector): BigDecimalVector = BigDecimalVector(that.size) { BigDecimalMonoid.one() / that[it] }


@JvmInline
value class BigDecimalMatrix(private val elements: MutableList<BigDecimalVector>) : Iterable<BigDecimalVector> {
    init {
        require(elements.isNotEmpty()) { "Matrix must have non-zero number of rows." }
        require(elements.map { it.size }.noneDistinct()) { "Matrix rows must all have the exact same size." }
    }

    constructor(size: Int, init: (Int) -> BigDecimalVector) : this((0..<size).mapTo(mutableListOf(), init))


    val height: Int get() = elements.size

    val rowIndices: IntRange get() = 0..<height

    val width: Int get() = elements[0].size

    val colIndices: IntRange get() = 0..<width

    val size: Pair<Int, Int> get() = height to width


    fun toList(): List<BigDecimalVector> = elements

    override fun iterator(): Iterator<BigDecimalVector> = elements.iterator()


    operator fun get(row: Int, col: Int): BigDecimal = elements[row][col]

    operator fun get(rowAndCol: Pair<Int, Int>): BigDecimal = get(rowAndCol.first, rowAndCol.second)

    fun getRow(row: Int): BigDecimalVector = elements[row]

    fun getRows(rows: Iterable<Int>): BigDecimalMatrix = BigDecimalMatrix(rows.mapTo(mutableListOf()) { getRow(it) })

    fun getCol(col: Int): BigDecimalVector = BigDecimalVector(Array<BigDecimal>(height) { get(it, col) })

    fun getRows(): List<BigDecimalVector> = elements

    fun getCols(): List<BigDecimalVector> = colIndices.map { getCol(it) }

    operator fun set(row: Int, col: Int, value: BigDecimal): Unit = elements[row].set(col, value)

    operator fun set(rowAndCol: Pair<Int, Int>, value: BigDecimal): Unit = set(rowAndCol.first, rowAndCol.second, value)

    fun setRow(row: Int, value: BigDecimalVector) = elements.set(row, value)

    fun setCol(col: Int, value: BigDecimalVector) = colIndices.forEach { this[it, col] = value[it] }

    fun swapRows(row1: Int, row2: Int) {
        elements[row1] = elements[row2].also { elements[row2] = elements[row1] }
    }


    fun transposed(): BigDecimalMatrix = BigDecimalMatrix(width) { getCol(it) }

    fun appendedHorz(that: BigDecimalMatrix): BigDecimalMatrix = BigDecimalMatrix(height) { this.getRow(it).appended(that.getRow(it)) }

    fun appendedVert(that: BigDecimalMatrix): BigDecimalMatrix =
        BigDecimalMatrix(this.elements.toMutableList().apply { addAll(that.elements) })


    operator fun plus(that: BigDecimalMatrix): BigDecimalMatrix = BigDecimalMatrix(height) { this.getRow(it) + that.getRow(it) }

    operator fun plusAssign(that: BigDecimalMatrix): Unit = rowIndices.forEach { this.getRow(it) += that.getRow(it) }

    operator fun unaryMinus(): BigDecimalMatrix = BigDecimalMatrix(height) { -getRow(it) }

    operator fun minus(that: BigDecimalMatrix): BigDecimalMatrix = BigDecimalMatrix(height) { this.getRow(it) - that.getRow(it) }

    operator fun minusAssign(that: BigDecimalMatrix): Unit = rowIndices.forEach { this.getRow(it) -= that.getRow(it) }

    operator fun times(that: BigDecimalMatrix): BigDecimalMatrix = BigDecimalMatrix(height) { this.getRow(it) * that.getRow(it) }

    operator fun timesAssign(that: BigDecimalMatrix): Unit = rowIndices.forEach { this.getRow(it) *= that.getRow(it) }

    operator fun div(that: BigDecimalMatrix): BigDecimalMatrix = BigDecimalMatrix(height) { this.getRow(it) / that.getRow(it) }

    operator fun divAssign(that: BigDecimalMatrix): Unit = rowIndices.forEach { this.getRow(it) /= that.getRow(it) }

    fun dot(that: BigDecimalVector): BigDecimalVector {
        require(width == that.size) { "Incompatible matrix size $size and vector ${that.size}." }
        return elements.map { it.dot(that) }.toVector()
    }
}

fun List<List<BigDecimal>>.toMatrix(): BigDecimalMatrix = BigDecimalMatrix(mapTo(mutableListOf()) { it.toVector() })
