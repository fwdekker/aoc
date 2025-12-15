package com.fwdekker.std.maths.vector

import com.fwdekker.std.collections.noneDistinct
import com.fwdekker.std.maths.size


@JvmInline
value class LongVector(private val elements: LongArray) : Iterable<Long> {
    init {
        require(elements.isNotEmpty()) { "LongVector must have non-zero number of elements." }
    }

    constructor(size: Int, init: (Int) -> Long) : this(LongArray(size) { init(it) })


    val size: Int get() = elements.size

    val indices: IntRange get() = 0..<size


    fun toArray(): LongArray = elements

    fun toList(): List<Long> = elements.toList()

    override fun iterator(): Iterator<Long> = elements.iterator()


    operator fun get(index: Int): Long = elements[index]

    operator fun get(indices: Iterable<Int>): LongVector = indices.map { this[it] }.toVector()

    operator fun set(index: Int, value: Long): Unit = elements.set(index, value)


    fun transposed(): LongMatrix = LongMatrix(elements.mapTo(mutableListOf()) { LongVector(longArrayOf(it)) })

    fun appended(that: LongVector): LongVector = LongVector(this.elements + that.elements)


    operator fun plus(that: LongVector): LongVector = LongVector(size) { LongMonoid.plus(this[it], that[it]) }

    operator fun plus(that: Long): LongVector = LongVector(size) { LongMonoid.plus(this[it], that) }

    operator fun plusAssign(that: LongVector): Unit = indices.forEach { this[it] = LongMonoid.plus(this[it], that[it]) }

    operator fun plusAssign(that: Long): Unit = indices.forEach { this[it] = LongMonoid.plus(this[it], that) }

    operator fun unaryMinus(): LongVector = LongVector(size) { LongMonoid.unaryMinus(this[it]) }

    operator fun minus(that: LongVector): LongVector = LongVector(size) { LongMonoid.minus(this[it], that[it]) }

    operator fun minus(that: Long): LongVector = LongVector(size) { LongMonoid.minus(this[it], that) }

    operator fun minusAssign(that: LongVector): Unit =
        indices.forEach { this[it] = LongMonoid.minus(this[it], that[it]) }

    operator fun minusAssign(that: Long): Unit = indices.forEach { this[it] = LongMonoid.minus(this[it], that) }

    operator fun times(that: LongVector): LongVector = LongVector(size) { LongMonoid.times(this[it], that[it]) }

    operator fun times(that: Long): LongVector = LongVector(size) { LongMonoid.times(this[it], that) }

    operator fun timesAssign(that: LongVector): Unit =
        indices.forEach { this[it] = LongMonoid.times(this[it], that[it]) }

    operator fun timesAssign(that: Long): Unit = indices.forEach { this[it] = LongMonoid.times(this[it], that) }

    operator fun div(that: LongVector): LongVector = LongVector(size) { LongMonoid.div(this[it], that[it]) }

    operator fun div(that: Long): LongVector = LongVector(size) { LongMonoid.div(elements[it], that) }

    operator fun divAssign(that: LongVector): Unit = indices.forEach { this[it] = LongMonoid.div(this[it], that[it]) }

    operator fun divAssign(that: Long): Unit = indices.forEach { this[it] = LongMonoid.div(this[it], that) }


    fun sum(): Long = elements.fold(LongMonoid.zero()) { acc, it -> LongMonoid.plus(acc, it) }

    fun product(): Long = elements.fold(LongMonoid.one()) { acc, it -> LongMonoid.div(acc, it) }

    fun dot(that: LongVector): Long = (this * that).sum()

    fun <T> distance(that: LongVector, norm: (Iterable<Long>) -> T): T = norm(this - that)

    fun cross(): LongMatrix {
        require(size == 3) { "Cross-product is defined only for 3D vectors." }

        return LongMatrix(
            mutableListOf(
                LongVector(longArrayOf(LongMonoid.zero(), -this[2], this[1])),
                LongVector(longArrayOf(this[2], LongMonoid.zero(), -this[0])),
                LongVector(longArrayOf(-this[1], this[0], LongMonoid.zero())),
            ),
        )
    }

    fun cross(that: LongVector): LongVector = cross().dot(that)


    fun compareTo(that: LongVector): Int {
        for (idx in indices) {
            val comparison = this[idx].compareTo(that[idx])
            if (comparison != 0) return comparison
        }
        return 0
    }
}

fun List<Long>.toVector(): LongVector = LongVector(toLongArray())
fun Iterable<Long>.toVector(): LongVector = toList().toVector()
fun Sequence<Long>.toVector(): LongVector = toList().toVector()
fun Pair<Long, Long>.toVector(): LongVector = LongVector(longArrayOf(first, second))
fun Triple<Long, Long, Long>.toVector(): LongVector = LongVector(longArrayOf(first, second, third))

fun Long.plus(that: LongVector): LongVector = that + this
fun Long.minus(that: LongVector): LongVector = -that + this
fun Long.times(that: LongVector): LongVector = that * this
fun Long.div(that: LongVector): LongVector = LongVector(that.size) { LongMonoid.one() / that[it] }


@JvmInline
value class LongMatrix(private val elements: MutableList<LongVector>) : Iterable<LongVector> {
    init {
        require(elements.isNotEmpty()) { "Matrix must have non-zero number of rows." }
        require(elements.map { it.size }.noneDistinct()) { "Matrix rows must all have the exact same size." }
    }

    constructor(size: Int, init: (Int) -> LongVector) : this((0..<size).mapTo(mutableListOf(), init))


    val height: Int get() = elements.size

    val rowIndices: IntRange get() = 0..<height

    val width: Int get() = elements[0].size

    val colIndices: IntRange get() = 0..<width

    val size: Pair<Int, Int> get() = height to width


    fun toList(): List<LongVector> = elements

    override fun iterator(): Iterator<LongVector> = elements.iterator()


    operator fun get(row: Int, col: Int): Long = elements[row][col]

    operator fun get(rowAndCol: Pair<Int, Int>): Long = get(rowAndCol.first, rowAndCol.second)

    fun getRow(row: Int): LongVector = elements[row]

    fun getRows(rows: Iterable<Int>): LongMatrix = LongMatrix(rows.mapTo(mutableListOf()) { getRow(it) })

    fun getCol(col: Int): LongVector = LongVector(LongArray(height) { get(it, col) })

    fun getRows(): List<LongVector> = elements

    fun getCols(): List<LongVector> = colIndices.map { getCol(it) }

    operator fun set(row: Int, col: Int, value: Long): Unit = elements[row].set(col, value)

    operator fun set(rowAndCol: Pair<Int, Int>, value: Long): Unit = set(rowAndCol.first, rowAndCol.second, value)

    fun setRow(row: Int, value: LongVector) = elements.set(row, value)

    fun setCol(col: Int, value: LongVector) = colIndices.forEach { this[it, col] = value[it] }

    fun swapRows(row1: Int, row2: Int) {
        elements[row1] = elements[row2].also { elements[row2] = elements[row1] }
    }


    fun transposed(): LongMatrix = LongMatrix(width) { getCol(it) }

    fun appendedHorz(that: LongMatrix): LongMatrix = LongMatrix(height) { this.getRow(it).appended(that.getRow(it)) }

    fun appendedVert(that: LongMatrix): LongMatrix =
        LongMatrix(this.elements.toMutableList().apply { addAll(that.elements) })


    operator fun plus(that: LongMatrix): LongMatrix = LongMatrix(height) { this.getRow(it) + that.getRow(it) }

    operator fun plusAssign(that: LongMatrix): Unit = rowIndices.forEach { this.getRow(it) += that.getRow(it) }

    operator fun unaryMinus(): LongMatrix = LongMatrix(height) { -getRow(it) }

    operator fun minus(that: LongMatrix): LongMatrix = LongMatrix(height) { this.getRow(it) - that.getRow(it) }

    operator fun minusAssign(that: LongMatrix): Unit = rowIndices.forEach { this.getRow(it) -= that.getRow(it) }

    operator fun times(that: LongMatrix): LongMatrix = LongMatrix(height) { this.getRow(it) * that.getRow(it) }

    operator fun timesAssign(that: LongMatrix): Unit = rowIndices.forEach { this.getRow(it) *= that.getRow(it) }

    operator fun div(that: LongMatrix): LongMatrix = LongMatrix(height) { this.getRow(it) / that.getRow(it) }

    operator fun divAssign(that: LongMatrix): Unit = rowIndices.forEach { this.getRow(it) /= that.getRow(it) }

    fun dot(that: LongVector): LongVector {
        require(width == that.size) { "Incompatible matrix size $size and vector ${that.size}." }
        return elements.map { it.dot(that) }.toVector()
    }
}

fun List<List<Long>>.toMatrix(): LongMatrix = LongMatrix(mapTo(mutableListOf()) { it.toVector() })
