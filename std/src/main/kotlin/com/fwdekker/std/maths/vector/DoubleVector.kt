/// Kotlin template. DO NOT EDIT DIRECTLY.
/// Base: LongVector.kt
/// Replace: Long=>Double
/// Replace: long=>double
package com.fwdekker.std.maths.vector

import com.fwdekker.std.collections.noneDistinct
import com.fwdekker.std.maths.size


@JvmInline
value class DoubleVector(private val elements: DoubleArray) : Iterable<Double> {
    init {
        require(elements.isNotEmpty()) { "DoubleVector must have non-zero number of elements." }
    }

    constructor(size: Int, init: (Int) -> Double) : this(DoubleArray(size) { init(it) })


    val size: Int get() = elements.size

    val indices: IntRange get() = 0..<size


    fun toArray(): DoubleArray = elements

    fun toList(): List<Double> = elements.toList()

    override fun iterator(): Iterator<Double> = elements.iterator()


    operator fun get(index: Int): Double = elements[index]

    operator fun get(indices: Iterable<Int>): DoubleVector = indices.map { this[it] }.toVector()

    operator fun set(index: Int, value: Double): Unit = elements.set(index, value)


    fun transposed(): DoubleMatrix = DoubleMatrix(elements.mapTo(mutableListOf()) { DoubleVector(doubleArrayOf(it)) })

    fun appended(that: DoubleVector): DoubleVector = DoubleVector(this.elements + that.elements)


    operator fun plus(that: DoubleVector): DoubleVector = DoubleVector(size) { DoubleMonoid.plus(this[it], that[it]) }

    operator fun plus(that: Double): DoubleVector = DoubleVector(size) { DoubleMonoid.plus(this[it], that) }

    operator fun plusAssign(that: DoubleVector): Unit = indices.forEach { this[it] = DoubleMonoid.plus(this[it], that[it]) }

    operator fun plusAssign(that: Double): Unit = indices.forEach { this[it] = DoubleMonoid.plus(this[it], that) }

    operator fun unaryMinus(): DoubleVector = DoubleVector(size) { DoubleMonoid.unaryMinus(this[it]) }

    operator fun minus(that: DoubleVector): DoubleVector = DoubleVector(size) { DoubleMonoid.minus(this[it], that[it]) }

    operator fun minus(that: Double): DoubleVector = DoubleVector(size) { DoubleMonoid.minus(this[it], that) }

    operator fun minusAssign(that: DoubleVector): Unit =
        indices.forEach { this[it] = DoubleMonoid.minus(this[it], that[it]) }

    operator fun minusAssign(that: Double): Unit = indices.forEach { this[it] = DoubleMonoid.minus(this[it], that) }

    operator fun times(that: DoubleVector): DoubleVector = DoubleVector(size) { DoubleMonoid.times(this[it], that[it]) }

    operator fun times(that: Double): DoubleVector = DoubleVector(size) { DoubleMonoid.times(this[it], that) }

    operator fun timesAssign(that: DoubleVector): Unit =
        indices.forEach { this[it] = DoubleMonoid.times(this[it], that[it]) }

    operator fun timesAssign(that: Double): Unit = indices.forEach { this[it] = DoubleMonoid.times(this[it], that) }

    operator fun div(that: DoubleVector): DoubleVector = DoubleVector(size) { DoubleMonoid.div(this[it], that[it]) }

    operator fun div(that: Double): DoubleVector = DoubleVector(size) { DoubleMonoid.div(elements[it], that) }

    operator fun divAssign(that: DoubleVector): Unit = indices.forEach { this[it] = DoubleMonoid.div(this[it], that[it]) }

    operator fun divAssign(that: Double): Unit = indices.forEach { this[it] = DoubleMonoid.div(this[it], that) }


    fun sum(): Double = elements.fold(DoubleMonoid.zero()) { acc, it -> DoubleMonoid.plus(acc, it) }

    fun product(): Double = elements.fold(DoubleMonoid.one()) { acc, it -> DoubleMonoid.div(acc, it) }

    fun dot(that: DoubleVector): Double = (this * that).sum()

    fun <T> distance(that: DoubleVector, norm: (Iterable<Double>) -> T): T = norm(this - that)

    fun cross(): DoubleMatrix {
        require(size == 3) { "Cross-product is defined only for 3D vectors." }

        return DoubleMatrix(
            mutableListOf(
                DoubleVector(doubleArrayOf(DoubleMonoid.zero(), -this[2], this[1])),
                DoubleVector(doubleArrayOf(this[2], DoubleMonoid.zero(), -this[0])),
                DoubleVector(doubleArrayOf(-this[1], this[0], DoubleMonoid.zero())),
            ),
        )
    }

    fun cross(that: DoubleVector): DoubleVector = cross().dot(that)


    fun compareTo(that: DoubleVector): Int {
        for (idx in indices) {
            val comparison = this[idx].compareTo(that[idx])
            if (comparison != 0) return comparison
        }
        return 0
    }
}

fun List<Double>.toVector(): DoubleVector = DoubleVector(toDoubleArray())
fun Iterable<Double>.toVector(): DoubleVector = toList().toVector()
fun Sequence<Double>.toVector(): DoubleVector = toList().toVector()
fun Pair<Double, Double>.toVector(): DoubleVector = DoubleVector(doubleArrayOf(first, second))
fun Triple<Double, Double, Double>.toVector(): DoubleVector = DoubleVector(doubleArrayOf(first, second, third))

fun Double.plus(that: DoubleVector): DoubleVector = that + this
fun Double.minus(that: DoubleVector): DoubleVector = -that + this
fun Double.times(that: DoubleVector): DoubleVector = that * this
fun Double.div(that: DoubleVector): DoubleVector = DoubleVector(that.size) { DoubleMonoid.one() / that[it] }


@JvmInline
value class DoubleMatrix(private val elements: MutableList<DoubleVector>) : Iterable<DoubleVector> {
    init {
        require(elements.isNotEmpty()) { "Matrix must have non-zero number of rows." }
        require(elements.map { it.size }.noneDistinct()) { "Matrix rows must all have the exact same size." }
    }

    constructor(size: Int, init: (Int) -> DoubleVector) : this((0..<size).mapTo(mutableListOf(), init))


    val height: Int get() = elements.size

    val rowIndices: IntRange get() = 0..<height

    val width: Int get() = elements[0].size

    val colIndices: IntRange get() = 0..<width

    val size: Pair<Int, Int> get() = height to width


    fun toList(): List<DoubleVector> = elements

    override fun iterator(): Iterator<DoubleVector> = elements.iterator()


    operator fun get(row: Int, col: Int): Double = elements[row][col]

    operator fun get(rowAndCol: Pair<Int, Int>): Double = get(rowAndCol.first, rowAndCol.second)

    fun getRow(row: Int): DoubleVector = elements[row]

    fun getRows(rows: Iterable<Int>): DoubleMatrix = DoubleMatrix(rows.mapTo(mutableListOf()) { getRow(it) })

    fun getCol(col: Int): DoubleVector = DoubleVector(DoubleArray(height) { get(it, col) })

    fun getRows(): List<DoubleVector> = elements

    fun getCols(): List<DoubleVector> = colIndices.map { getCol(it) }

    operator fun set(row: Int, col: Int, value: Double): Unit = elements[row].set(col, value)

    operator fun set(rowAndCol: Pair<Int, Int>, value: Double): Unit = set(rowAndCol.first, rowAndCol.second, value)

    fun setRow(row: Int, value: DoubleVector) = elements.set(row, value)

    fun setCol(col: Int, value: DoubleVector) = colIndices.forEach { this[it, col] = value[it] }

    fun swapRows(row1: Int, row2: Int) {
        elements[row1] = elements[row2].also { elements[row2] = elements[row1] }
    }


    fun transposed(): DoubleMatrix = DoubleMatrix(width) { getCol(it) }

    fun appendedHorz(that: DoubleMatrix): DoubleMatrix = DoubleMatrix(height) { this.getRow(it).appended(that.getRow(it)) }

    fun appendedVert(that: DoubleMatrix): DoubleMatrix =
        DoubleMatrix(this.elements.toMutableList().apply { addAll(that.elements) })


    operator fun plus(that: DoubleMatrix): DoubleMatrix = DoubleMatrix(height) { this.getRow(it) + that.getRow(it) }

    operator fun plusAssign(that: DoubleMatrix): Unit = rowIndices.forEach { this.getRow(it) += that.getRow(it) }

    operator fun unaryMinus(): DoubleMatrix = DoubleMatrix(height) { -getRow(it) }

    operator fun minus(that: DoubleMatrix): DoubleMatrix = DoubleMatrix(height) { this.getRow(it) - that.getRow(it) }

    operator fun minusAssign(that: DoubleMatrix): Unit = rowIndices.forEach { this.getRow(it) -= that.getRow(it) }

    operator fun times(that: DoubleMatrix): DoubleMatrix = DoubleMatrix(height) { this.getRow(it) * that.getRow(it) }

    operator fun timesAssign(that: DoubleMatrix): Unit = rowIndices.forEach { this.getRow(it) *= that.getRow(it) }

    operator fun div(that: DoubleMatrix): DoubleMatrix = DoubleMatrix(height) { this.getRow(it) / that.getRow(it) }

    operator fun divAssign(that: DoubleMatrix): Unit = rowIndices.forEach { this.getRow(it) /= that.getRow(it) }

    fun dot(that: DoubleVector): DoubleVector {
        require(width == that.size) { "Incompatible matrix size $size and vector ${that.size}." }
        return elements.map { it.dot(that) }.toVector()
    }
}

fun List<List<Double>>.toMatrix(): DoubleMatrix = DoubleMatrix(mapTo(mutableListOf()) { it.toVector() })
