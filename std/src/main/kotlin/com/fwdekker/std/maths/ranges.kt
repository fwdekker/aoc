package com.fwdekker.std.maths

import java.math.BigInteger


/**
 * A range of [BigInteger]s.
 */
class BigIntegerRange(
    override val start: BigInteger,
    override val endInclusive: BigInteger,
) : ClosedRange<BigInteger>, Iterable<BigInteger> {
    override operator fun iterator(): Iterator<BigInteger> =
        iterator {
            var current = start
            while (current <= endInclusive) yield(current++)
        }
}

/**
 * Creates a [BigIntegerRange] from [this] up to and including [endInclusive].
 */
fun BigInteger.rangeTo(endInclusive: BigInteger): BigIntegerRange = BigIntegerRange(this, endInclusive)

/**
 * Creates a [BigIntegerRange] from [this] up to but excluding [endExclusive].
 */
fun BigInteger.rangeUntil(endExclusive: BigInteger): BigIntegerRange = BigIntegerRange(this, endExclusive.dec())


/**
 * Creates a range from [Pair.first] up to and including [Pair.second].
 */
fun Pair<Int, Int>.asRangeTo(): IntRange = first..second

fun Pair<Long, Long>.asRangeTo(): LongRange = first..second

fun Pair<BigInteger, BigInteger>.asRangeTo(): BigIntegerRange = first.rangeTo(second)

/**
 * Creates a range from [Pair.first] up to and excluding [Pair.second].
 */
fun Pair<Int, Int>.asRangeUntil(): IntRange = first..<second

fun Pair<Long, Long>.asRangeUntil(): LongRange = first..<second

fun Pair<BigInteger, BigInteger>.asRangeUntil(): BigIntegerRange = first.rangeUntil(second)


/**
 * Splits this range into two parts signifying which elements are less than or equal to [value].
 *
 * The first part contains all elements less than or equal to [value], and the second part contains all elements
 * strictly greater than [value].
 */
fun IntRange.splitLEQ(value: Int): Pair<IntRange, IntRange> =
    when {
        isEmpty() -> Pair(IntRange.EMPTY, IntRange.EMPTY)
        value > last -> Pair(this, IntRange.EMPTY)
        value <= first -> Pair(IntRange.EMPTY, this)
        else -> Pair(first..<value, value..last)
    }

/**
 * Splits this range into two parts signifying which elements are less than or equal to [value].
 *
 * The first part contains all elements less than or equal to [value], and the second part contains all elements
 * strictly greater than [value].
 */
fun LongRange.splitLEQ(value: Long): Pair<LongRange, LongRange> =
    when {
        isEmpty() -> Pair(LongRange.EMPTY, LongRange.EMPTY)
        value > last -> Pair(this, LongRange.EMPTY)
        value <= first -> Pair(LongRange.EMPTY, this)
        else -> Pair(first..<value, value..last)
    }

/**
 * Splits this range into two parts signifying which elements are greater than or equal to [value].
 *
 * The first part contains all elements greater than or equal to [value], and the second part contains all elements
 * strictly less than [value].
 */
fun IntRange.splitGEQ(value: Int): Pair<IntRange, IntRange> =
    splitLEQ(value + 1).let { Pair(it.second, it.first) }

/**
 * Splits this range into two parts signifying which elements are greater than or equal to [value].
 *
 * The first part contains all elements greater than or equal to [value], and the second part contains all elements
 * strictly less than [value].
 */
fun LongRange.splitGEQ(value: Long): Pair<LongRange, LongRange> =
    splitLEQ(value + 1).let { Pair(it.second, it.first) }

/**
 * Returns the range of values that lies in both [this] and [that].
 */
fun IntRange.overlap(that: IntRange): IntRange =
    (kotlin.math.max(this.first, that.first)..kotlin.math.min(this.last, that.last))
        .let { if (it.isEmpty()) IntRange.EMPTY else it }

/**
 * Returns the range of values that lies in both [this] and [that].
 */
fun LongRange.overlap(that: LongRange): LongRange =
    (kotlin.math.max(this.first, that.first)..kotlin.math.min(this.last, that.last))
        .let { if (it.isEmpty()) LongRange.EMPTY else it }

/**
 * Returns `true` if and only if [this] and [that] share at least one common value.
 */
fun IntRange.overlaps(that: IntRange): Boolean =
    !(kotlin.math.max(this.first, that.first)..kotlin.math.min(this.last, that.last)).isEmpty()

/**
 * Returns `true` if and only if [this] and [that] share at least one common value.
 */
fun LongRange.overlaps(that: LongRange): Boolean =
    !(kotlin.math.max(this.first, that.first)..kotlin.math.min(this.last, that.last)).isEmpty()

/**
 * Returns zero, one, or two ranges that together consist of all values in [this] that are not in [that].
 */
fun IntRange.without(that: IntRange): List<IntRange> =
    listOf(
        this.first..kotlin.math.min(this.last, that.first - 1),
        kotlin.math.max(this.first, that.last + 1)..this.last
    )
        .filterNot { it.isEmpty() }

/**
 * Returns zero, one, or two ranges that together consist of all values in [this] that are not in [that].
 */
fun LongRange.without(that: LongRange): List<LongRange> =
    listOf(
        this.first..kotlin.math.min(this.last, that.first - 1),
        kotlin.math.max(this.first, that.last + 1)..this.last
    )
        .filterNot { it.isEmpty() }

/**
 * Increases the start and end of the range by [by].
 */
fun IntRange.shift(by: Int): IntRange = this.first + by..this.last + by

/**
 * Increases the start and end of the range by [by].
 */
fun LongRange.shift(by: Long): LongRange = this.first + by..this.last + by
