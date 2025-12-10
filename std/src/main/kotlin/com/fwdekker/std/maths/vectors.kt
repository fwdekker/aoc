package com.fwdekker.std.maths

import com.fwdekker.std.collections.asPair
import com.fwdekker.std.collections.asTriple
import com.fwdekker.std.grid.transpose
import java.math.BigDecimal
import java.math.MathContext
import kotlin.collections.sumOf
import kotlin.math.abs


/**
 * Various sizes of vectors.
 */
typealias Vec<T> = List<T>
typealias Vec2<T> = Pair<T, T>
typealias Vec3<T> = Triple<T, T, T>

/**
 * Creates a shallow copy.
 */
@JvmName("vecCopy")
fun <T> Vec<T>.copy() = toList()

/**
 * Transposes this vector.
 */
val <T> Vec<T>.T: Mat<T> @JvmName("vecTranspose") get() = map { listOf(it) }
val <T> Vec2<T>.T: Mat<T> get() = toList().T
val <T> Vec3<T>.T: Mat<T> get() = toList().T

/**
 * Concatenates vectors.
 */
fun <T> Vec<T>.cat(that: Vec<T>): Vec<T> = this + that
fun <T> Vec<T>.cat(that: Vec2<T>): Vec<T> = this + that.toList()
fun <T> Vec<T>.cat(that: Vec3<T>): Vec<T> = this + that.toList()

fun <T> Vec2<T>.cat(that: Vec<T>): Vec<T> = toList().cat(that)
fun <T> Vec2<T>.cat(that: Vec2<T>): Vec<T> = toList().cat(that)
fun <T> Vec2<T>.cat(that: Vec3<T>): Vec<T> = toList().cat(that)

fun <T> Vec3<T>.cat(that: Vec<T>): Vec<T> = toList().cat(that)
fun <T> Vec3<T>.cat(that: Vec2<T>): Vec<T> = toList().cat(that)
fun <T> Vec3<T>.cat(that: Vec3<T>): Vec<T> = toList().cat(that)

/**
 * Creates a total ordering over all vectors, comparing them element-wise and returning the first non-zero comparison
 * of elements.
 */
operator fun <T : Comparable<T>> Vec<T>.compareTo(that: Vec<T>): Int {
    val zipped = (this zip that)
    for ((a, b) in zipped) {
        val compared = a.compareTo(b)
        if (compared != 0) return compared
    }
    return 0
}

operator fun <T : Comparable<T>> Vec2<T>.compareTo(that: Vec2<T>): Int {
    val first = this.first.compareTo(that.first)
    if (first != 0) return first
    return this.second.compareTo(that.second)
}

operator fun <T : Comparable<T>> Vec3<T>.compareTo(that: Vec3<T>): Int {
    val first = this.first.compareTo(that.first)
    if (first != 0) return first
    val second = this.second.compareTo(that.second)
    if (second != 0) return second
    return this.third.compareTo(that.third)
}


/**
 * Typed vectors.
 */
typealias IVec = Vec<Int>
typealias IVec2 = Vec2<Int>
typealias IVec3 = Vec3<Int>

typealias LVec = Vec<Long>
typealias LVec2 = Vec2<Long>
typealias LVec3 = Vec3<Long>

typealias BDVec = Vec<BigDecimal>
typealias BDVec2 = Vec2<BigDecimal>
typealias BDVec3 = Vec3<BigDecimal>

/**
 * Negates all elements of the vector.
 */
@JvmName("intVegNeg")
fun IVec.vecNeg(): IVec = map { -it }
@JvmName("intVegNeg")
fun IVec2.vecNeg(): IVec2 = Pair(-first, -second)
@JvmName("intVegNeg")
fun IVec3.vecNeg(): IVec3 = Triple(-first, -second, -third)

@JvmName("longVegNeg")
fun LVec.vecNeg(): LVec = map { -it }
@JvmName("longVegNeg")
fun LVec2.vecNeg(): LVec2 = Pair(-first, -second)
@JvmName("longVegNeg")
fun LVec3.vecNeg(): LVec3 = Triple(-first, -second, -third)

@JvmName("bigDecimalVegNeg")
fun BDVec.vecNeg(): BDVec = map { -it }
@JvmName("bigDecimalVegNeg")
fun BDVec2.vecNeg(): BDVec2 = Pair(-first, -second)
@JvmName("bigDecimalVegNeg")
fun BDVec3.vecNeg(): BDVec3 = Triple(-first, -second, -third)

/**
 * Sums elements at identical indices.
 */
@JvmName("intVecPlus")
fun IVec.vecPlus(that: IVec): IVec = (this zip that).map { (a, b) -> a + b }
@JvmName("intVecPlus")
fun IVec2.vecPlus(that: IVec2): IVec2 = Pair(first + that.first, second + that.second)
@JvmName("intVecPlus")
fun IVec3.vecPlus(that: IVec3): IVec3 = Triple(first + that.first, second + that.second, third + that.third)

@JvmName("longVecPlus")
fun LVec.vecPlus(that: LVec): LVec = (this zip that).map { (a, b) -> a + b }
@JvmName("longVecPlus")
fun LVec2.vecPlus(that: LVec2): LVec2 = Pair(first + that.first, second + that.second)
@JvmName("longVecPlus")
fun LVec3.vecPlus(that: LVec3): LVec3 = Triple(first + that.first, second + that.second, third + that.third)

@JvmName("bigDecimalVecPlus")
fun BDVec.vecPlus(that: BDVec): BDVec = (this zip that).map { (a, b) -> a + b }
@JvmName("bigDecimalVecPlus")
fun BDVec2.vecPlus(that: BDVec2): BDVec2 = Pair(first + that.first, second + that.second)
@JvmName("bigDecimalVecPlus")
fun BDVec3.vecPlus(that: BDVec3): BDVec3 = Triple(first + that.first, second + that.second, third + that.third)

/**
 * Subtracts elements at identical indices.
 */
@JvmName("intVecMinus")
fun IVec.vecMinus(that: IVec): IVec = vecPlus(that.vecNeg())
@JvmName("intVecMinus")
fun IVec2.vecMinus(that: IVec2): IVec2 = vecPlus(that.vecNeg())
@JvmName("intVecMinus")
fun IVec3.vecMinus(that: IVec3): IVec3 = vecPlus(that.vecNeg())

@JvmName("longVecMinus")
fun LVec.vecMinus(that: LVec): LVec = vecPlus(that.vecNeg())
@JvmName("longVecMinus")
fun LVec2.vecMinus(that: LVec2): LVec2 = vecPlus(that.vecNeg())
@JvmName("longVecMinus")
fun LVec3.vecMinus(that: LVec3): LVec3 = vecPlus(that.vecNeg())

@JvmName("bigDecimalVecMinus")
fun BDVec.vecMinus(that: BDVec): BDVec = vecPlus(that.vecNeg())
@JvmName("bigDecimalVecMinus")
fun BDVec2.vecMinus(that: BDVec2): BDVec2 = vecPlus(that.vecNeg())
@JvmName("bigDecimalVecMinus")
fun BDVec3.vecMinus(that: BDVec3): BDVec3 = vecPlus(that.vecNeg())

/**
 * Multiplies each element by the given factor.
 */
@JvmName("intVecTimes")
fun IVec.vecTimes(factor: Int): IVec = map { it * factor }
@JvmName("intVecTimes")
fun IVec2.vecTimes(factor: Int): IVec2 = Pair(first * factor, second * factor)
@JvmName("intVecTimes")
fun IVec3.vecTimes(factor: Int): IVec3 = Triple(first * factor, second * factor, third * factor)

@JvmName("longVecTimes")
fun LVec.vecTimes(factor: Long): LVec = map { it * factor }
@JvmName("longVecTimes")
fun LVec2.vecTimes(factor: Long): LVec2 = Pair(first * factor, second * factor)
@JvmName("longVecTimes")
fun LVec3.vecTimes(factor: Long): LVec3 = Triple(first * factor, second * factor, third * factor)

@JvmName("bigDecimalVecTimes")
fun BDVec.vecTimes(factor: BigDecimal): BDVec = map { it * factor }
@JvmName("bigDecimalVecTimes")
fun BDVec2.vecTimes(factor: BigDecimal): BDVec2 = Pair(first * factor, second * factor)
@JvmName("bigDecimalVecTimes")
fun BDVec3.vecTimes(factor: BigDecimal): BDVec3 = Triple(first * factor, second * factor, third * factor)

/**
 * Divides each element by the given value.
 */
@JvmName("intVecDivide")
fun IVec.vecDivide(factor: Int): IVec = map { it / factor }
@JvmName("intVecDivide")
fun IVec2.vecDivide(factor: Int): IVec2 = Pair(first / factor, second / factor)
@JvmName("intVecDivide")
fun IVec3.vecDivide(factor: Int): IVec3 = Triple(first / factor, second / factor, third / factor)

@JvmName("longVecDivide")
fun LVec.vecDivide(factor: Long): LVec = map { it / factor }
@JvmName("longVecDivide")
fun LVec2.vecDivide(factor: Long): LVec2 = Pair(first / factor, second / factor)
@JvmName("longVecDivide")
fun LVec3.vecDivide(factor: Long): LVec3 = Triple(first / factor, second / factor, third / factor)

@JvmName("bigDecimalVecDivide")
fun BDVec.vecDivide(factor: BigDecimal): BDVec = map { it.divide(factor, MathContext.DECIMAL128) }
@JvmName("bigDecimalVecDivide")
fun BDVec2.vecDivide(factor: BigDecimal): BDVec2 = Pair(first.divide(factor, MathContext.DECIMAL128), second.divide(factor, MathContext.DECIMAL128))
@JvmName("bigDecimalVecDivide")
fun BDVec3.vecDivide(factor: BigDecimal): BDVec3 = Triple(first.divide(factor, MathContext.DECIMAL128), second.divide(factor, MathContext.DECIMAL128), third.divide(factor, MathContext.DECIMAL128))

/**
 * Calculates the dot product of [this] with [that].
 */
@JvmName("intVecDot")
fun IVec.vecDot(that: IVec): Int = (this zip that).sumOf { (a, b) -> a * b }
@JvmName("intVecDot")
fun IVec2.vecDot(that: IVec2): Int = first * that.first + second * that.second
@JvmName("intVecDot")
fun IVec3.vecDot(that: IVec3): Int = first * that.first + second * that.second + third * that.third

@JvmName("longVecDot")
fun LVec.vecDot(that: LVec): Long = (this zip that).sumOf { (a, b) -> a * b }
@JvmName("longVecDot")
fun LVec2.vecDot(that: LVec2): Long = first * that.first + second * that.second
@JvmName("longVecDot")
fun LVec3.vecDot(that: LVec3): Long = first * that.first + second * that.second + third * that.third

@JvmName("bigDecimalVecDot")
fun BDVec.vecDot(that: BDVec): BigDecimal = (this zip that).sumOf { (a, b) -> a * b }
@JvmName("bigDecimalVecDot")
fun BDVec2.vecDot(that: BDVec2): BigDecimal = first * that.first + second * that.second
@JvmName("bigDecimalVecDot")
fun BDVec3.vecDot(that: BDVec3): BigDecimal = first * that.first + second * that.second + third * that.third

/**
 * Calculates the cross product of this vector.
 */
@JvmName("intCross")
fun IVec3.cross(): IMat = listOf(listOf(0, -third, second), listOf(third, 0, -first), listOf(-second, first, 0))
@JvmName("longCross")
fun LVec3.cross(): LMat = listOf(listOf(0L, -third, second), listOf(third, 0L, -first), listOf(-second, first, 0L))
@JvmName("bigDecimalCross")
fun BDVec3.cross(): BDMat = listOf(
    listOf(BigDecimal.ZERO, -third, second),
    listOf(third, BigDecimal.ZERO, -first),
    listOf(-second, first, BigDecimal.ZERO)
)

/**
 * Calculates the cross product of this vector with [that] vector.
 */
@JvmName("intCross")
fun IVec3.cross(that: IVec3): IVec3 = cross().matDot(that)
@JvmName("longCross")
fun LVec3.cross(that: LVec3): LVec3 = cross().matDot(that)
@JvmName("bigDecimalCross")
fun BDVec3.cross(that: BDVec3): BDVec3 = cross().matDot(that)

/**
 * Returns the [norm]-distance between [this] and [that], calculated as `norm(this - that)`.
 */
@JvmName("intDistance")
fun IVec.distance(that: IVec, norm: (Iterable<Int>) -> Int = { it.norm1() }): Int = norm(vecMinus(that))
@JvmName("intDistance")
fun IVec2.distance(that: IVec2, norm: (Iterable<Int>) -> Int = { it.norm1() }): Int = norm(vecMinus(that).toList())
@JvmName("intDistance")
fun IVec3.distance(that: IVec3, norm: (Iterable<Int>) -> Int = { it.norm1() }): Int = norm(vecMinus(that).toList())

@JvmName("longDistance")
fun LVec.distance(that: LVec, norm: (Iterable<Long>) -> Long = { it.norm1() }): Long = norm(vecMinus(that))
@JvmName("longDistance")
fun LVec2.distance(that: LVec2, norm: (Iterable<Long>) -> Long = { it.norm1() }): Long = norm(vecMinus(that).toList())
@JvmName("longDistance")
fun LVec3.distance(that: LVec3, norm: (Iterable<Long>) -> Long = { it.norm1() }): Long = norm(vecMinus(that).toList())

@JvmName("bigDecimalDistance")
fun BDVec.distance(that: BDVec, norm: (Iterable<BigDecimal>) -> BigDecimal = { it.norm1() }): BigDecimal =
    norm(vecMinus(that))
@JvmName("bigDecimalDistance")
fun BDVec2.distance(that: BDVec2, norm: (Iterable<BigDecimal>) -> BigDecimal = { it.norm1() }): BigDecimal =
    norm(vecMinus(that).toList())
@JvmName("bigDecimalDistance")
fun BDVec3.distance(that: BDVec3, norm: (Iterable<BigDecimal>) -> BigDecimal = { it.norm1() }): BigDecimal =
    norm(vecMinus(that).toList())


/**
 * A matrix.
 */
typealias Mat<T> = List<List<T>>

/**
 * Creates a shallow copy.
 */
@JvmName("matCopy")
fun <T> Mat<T>.copy() = map { it.toList() }

/**
 * Concatenates each row of [that] to each row of [this].
 */
fun <T> Mat<T>.horzCat(that: Mat<T>): Mat<T> = (this zip that).map { (a, b) -> a.cat(b) }

/**
 * Places the rows of [that] below the rows of [this].
 */
fun <T> Mat<T>.vertCat(that: Mat<T>): Mat<T> = this + that

/**
 * Transposes this matrix.
 */
val <T> Mat<T>.T: Mat<T> @JvmName("matTranspose") get() = transpose()

/**
 * Replaces the rows at the given indices with the associated rows.
 */
fun <T> Mat<T>.replaceRows(replacements: Map<Int, Vec<T>>): Mat<T> =
    mapIndexed { idx, oldRow -> replacements[idx] ?: oldRow.copy() }

/**
 * Creates a new [Mat] in which the given rows are swapped.
 */
fun <T> Mat<T>.swapRows(rowIdx1: Int, rowIdx2: Int): Mat<T> =
    replaceRows(mapOf(rowIdx1 to get(rowIdx2), rowIdx2 to get(rowIdx1)))


/**
 * Typed matrices.
 */
typealias IMat = Mat<Int>
typealias LMat = Mat<Long>
typealias BDMat = Mat<BigDecimal>

/**
 * Negates all elements of the matrix.
 */
@JvmName("intMatNeg")
fun IMat.matNeg(): IMat = map { it.vecNeg() }
@JvmName("longMatNeg")
fun LMat.matNeg(): LMat = map { it.vecNeg() }
@JvmName("bigDecimalMatNeg")
fun BDMat.matNeg(): BDMat = map { it.vecNeg() }

/**
 * Sums elements at identical indices.
 */
@JvmName("intMatPlus")
fun IMat.matPlus(that: IMat): IMat = (this zip that).map { (a, b) -> a.vecPlus(b) }
@JvmName("longMatPlus")
fun LMat.matPlus(that: LMat): LMat = (this zip that).map { (a, b) -> a.vecPlus(b) }
@JvmName("bigDecimalMatPlus")
fun BDMat.matPlus(that: BDMat): BDMat = (this zip that).map { (a, b) -> a.vecPlus(b) }

/**
 * Subtracts elements at identical indices.
 */
@JvmName("intMatMinus")
fun IMat.matMinus(that: IMat): IMat = matPlus(that.matNeg())
@JvmName("longMatMinus")
fun LMat.matMinus(that: LMat): LMat = matPlus(that.matNeg())
@JvmName("bigDecimalMatMinus")
fun BDMat.matMinus(that: BDMat): BDMat = matPlus(that.matNeg())

/**
 * Calculates the dot product between [this] and [that].
 */
@JvmName("intMatDot")
fun IMat.matDot(that: IVec): IVec = map { it.vecDot(that) }
@JvmName("intMatDot")
fun IMat.matDot(that: IVec2): IVec2 = map { it.vecDot(that.toList()) }.asPair()
@JvmName("intMatDot")
fun IMat.matDot(that: IVec3): IVec3 = map { it.vecDot(that.toList()) }.asTriple()

@JvmName("longMatDot")
fun LMat.matDot(that: LVec): LVec = map { it.vecDot(that) }
@JvmName("longMatDot")
fun LMat.matDot(that: LVec2): LVec2 = map { it.vecDot(that.toList()) }.asPair()
@JvmName("longMatDot")
fun LMat.matDot(that: LVec3): LVec3 = map { it.vecDot(that.toList()) }.asTriple()

@JvmName("bigDecimalMatDot")
fun BDMat.matDot(that: BDVec): BDVec = map { it.vecDot(that) }
@JvmName("bigDecimalMatDot")
fun BDMat.matDot(that: BDVec2): BDVec2 = map { it.vecDot(that.toList()) }.asPair()
@JvmName("bigDecimalMatDot")
fun BDMat.matDot(that: BDVec3): BDVec3 = map { it.vecDot(that.toList()) }.asTriple()

/**
 * Divides each element in the [rowIdx]th row by [factor].
 */
@JvmName("intDivideRow")
fun IMat.divideRow(rowIdx: Int, factor: Int): IMat =
    replaceRows(mapOf(rowIdx to get(rowIdx).vecDivide(factor)))
@JvmName("longDivideRow")
fun LMat.divideRow(rowIdx: Int, factor: Long): LMat =
    replaceRows(mapOf(rowIdx to get(rowIdx).vecDivide(factor)))
@JvmName("bigDecimalDivideRow")
fun BDMat.divideRow(rowIdx: Int, factor: BigDecimal): BDMat =
    replaceRows(mapOf(rowIdx to get(rowIdx).vecDivide(factor)))

/**
 * Adds [factor] multiples of [rowIdx2] to [rowIdx1].
 */
@JvmName("intAddRowMultiple")
fun IMat.addRowMultiple(rowIdx1: Int, rowIdx2: Int, factor: Int): IMat =
    replaceRows(mapOf(rowIdx1 to get(rowIdx1).vecPlus(get(rowIdx2).vecTimes(factor))))
@JvmName("longAddRowMultiple")
fun LMat.addRowMultiple(rowIdx1: Int, rowIdx2: Int, factor: Long): LMat =
    replaceRows(mapOf(rowIdx1 to get(rowIdx1).vecPlus(get(rowIdx2).vecTimes(factor))))
@JvmName("bigDecimalAddRowMultiple")
fun BDMat.addRowMultiple(rowIdx1: Int, rowIdx2: Int, factor: BigDecimal): BDMat =
    replaceRows(mapOf(rowIdx1 to get(rowIdx1).vecPlus(get(rowIdx2).vecTimes(factor))))


/**
 * Calculates the reduced row echelon form.
 */
private inline fun <T : Comparable<T>> Mat<T>.myRref(
    zero: T,
    abs: (T) -> T,
    neg: (T) -> T,
    addRowMultiple: (Mat<T>, Int, Int, T) -> Mat<T>,
    divideRow: (Mat<T>, Int, T) -> Mat<T>,
): Mat<T> {
    var output = this

    val rowIndices = this.indices
    val colIndices = this[0].indices

    var pivotRow = 0
    var pivotCol = 0
    while (pivotRow in rowIndices && pivotCol in colIndices) {
        val candidate = rowIndices.drop(pivotRow).maxBy { abs(output[it][pivotCol]) }
        if (output[candidate][pivotCol].compareTo(zero) == 0) {
            pivotCol++
            continue
        }

        output = output.swapRows(pivotRow, candidate)

        if (output[pivotRow][pivotCol].compareTo(zero) != 0)
            output = divideRow(output, pivotRow, output[pivotRow][pivotCol])

        output = rowIndices.minus(pivotRow)
            .fold(output) { acc, it -> addRowMultiple(acc, it, pivotRow, neg(acc[it][pivotCol])) }

        pivotRow++
        pivotCol++
    }

    return output
}

@JvmName("intRref")
fun IMat.rref(): IMat = myRref(
    0,
    { abs(it) },
    { -it },
    { mat, a, b, factor -> mat.addRowMultiple(a, b, factor) },
    { mat, idx, factor -> mat.divideRow(idx, factor) },
)

@JvmName("longRref")
fun LMat.rref(): LMat = myRref(
    0L,
    { abs(it) },
    { -it },
    { mat, a, b, factor -> mat.addRowMultiple(a, b, factor) },
    { mat, idx, factor -> mat.divideRow(idx, factor) },
)

@JvmName("bigDecimalRref")
fun BDMat.rref(): BDMat = myRref(
    BigDecimal.ZERO,
    { it.abs() },
    { -it },
    { mat, a, b, factor -> mat.addRowMultiple(a, b, factor) },
    { mat, idx, factor -> mat.divideRow(idx, factor) },
)
