package com.fwdekker.std.maths.vector

import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode
import kotlin.math.absoluteValue
import kotlin.math.pow


interface Monoid<N> {
    fun zero(): N
    fun one(): N
    fun plus(a: N, b: N): N
    fun unaryMinus(a: N): N
    fun minus(a: N, b: N): N
    fun times(a: N, b: N): N
    fun div(a: N, b: N): N
    fun pow(a: N, b: Int): Double
    fun sqrt(a: N): N
    fun abs(a: N): N
    fun floor(a: N): N
    fun ceil(a: N): N
    fun compareTo(a: N, b: N): Int
}

context(monoid: Monoid<N>)
fun <N> Iterable<N>.sum(): N = fold(monoid.zero(), monoid::plus)

context(monoid: Monoid<N>)
fun <T, N> Iterable<T>.sumOf(map: (T) -> N): N = map(map).sum()

context(monoid: Monoid<N>)
fun <N> Iterable<N>.product(): N = fold(monoid.one(), monoid::times)

context(monoid: Monoid<N>)
fun <T, N> Iterable<T>.productOf(map: (T) -> N): N = map(map).product()

context(monoid: Monoid<N>)
fun <N> Iterable<N>.min(): N = minWith(monoid::compareTo)

context(monoid: Monoid<N>)
fun <N> Iterable<N>.max(): N = maxWith(monoid::compareTo)


@Suppress("NOTHING_TO_INLINE", "OVERRIDE_BY_INLINE")
object IntMonoid : Monoid<Int> {
    override inline fun zero(): Int = 0
    override inline fun one(): Int = 1
    override inline fun plus(a: Int, b: Int): Int = a + b
    override inline fun unaryMinus(a: Int): Int = -a
    override inline fun minus(a: Int, b: Int): Int = a - b
    override inline fun times(a: Int, b: Int): Int = a * b
    override inline fun div(a: Int, b: Int): Int = a / b
    override inline fun pow(a: Int, b: Int): Double = a.toDouble().pow(b)
    override inline fun sqrt(a: Int): Int = kotlin.math.sqrt(a.toDouble()).toInt()
    override inline fun abs(a: Int): Int = a.absoluteValue
    override inline fun floor(a: Int): Int = a
    override inline fun ceil(a: Int): Int = a
    override inline fun compareTo(a: Int, b: Int): Int = a.compareTo(b)
}

@Suppress("NOTHING_TO_INLINE", "OVERRIDE_BY_INLINE")
object LongMonoid : Monoid<Long> {
    override inline fun zero(): Long = 0L
    override inline fun one(): Long = 1L
    override inline fun plus(a: Long, b: Long): Long = a + b
    override inline fun unaryMinus(a: Long): Long = -a
    override inline fun minus(a: Long, b: Long): Long = a - b
    override inline fun times(a: Long, b: Long): Long = a * b
    override inline fun div(a: Long, b: Long): Long = a / b
    override inline fun pow(a: Long, b: Int): Double = a.toDouble().pow(b)
    override inline fun sqrt(a: Long): Long = kotlin.math.sqrt(a.toDouble()).toLong()
    override inline fun abs(a: Long): Long = a.absoluteValue
    override inline fun floor(a: Long): Long = a
    override inline fun ceil(a: Long): Long = a
    override inline fun compareTo(a: Long, b: Long): Int = a.compareTo(b)
}

@Suppress("NOTHING_TO_INLINE", "OVERRIDE_BY_INLINE")
object FloatMonoid : Monoid<Float> {
    override inline fun zero(): Float = 0.0f
    override inline fun one(): Float = 1.0f
    override inline fun plus(a: Float, b: Float): Float = a + b
    override inline fun unaryMinus(a: Float): Float = -a
    override inline fun minus(a: Float, b: Float): Float = a - b
    override inline fun times(a: Float, b: Float): Float = a * b
    override inline fun div(a: Float, b: Float): Float = a / b
    override inline fun pow(a: Float, b: Int): Double = a.pow(b).toDouble()
    override inline fun sqrt(a: Float): Float = kotlin.math.sqrt(a)
    override inline fun abs(a: Float): Float = a.absoluteValue
    override inline fun floor(a: Float): Float = kotlin.math.floor(a)
    override inline fun ceil(a: Float): Float = kotlin.math.ceil(a)
    override inline fun compareTo(a: Float, b: Float): Int = a.compareTo(b)
}

@Suppress("NOTHING_TO_INLINE", "OVERRIDE_BY_INLINE")
object DoubleMonoid : Monoid<Double> {
    override inline fun zero(): Double = 0.0
    override inline fun one(): Double = 1.0
    override inline fun plus(a: Double, b: Double): Double = a + b
    override inline fun unaryMinus(a: Double): Double = -a
    override inline fun minus(a: Double, b: Double): Double = a - b
    override inline fun times(a: Double, b: Double): Double = a * b
    override inline fun div(a: Double, b: Double): Double = a / b
    override inline fun pow(a: Double, b: Int): Double = a.pow(b)
    override inline fun sqrt(a: Double): Double = kotlin.math.sqrt(a)
    override inline fun abs(a: Double): Double = a.absoluteValue
    override inline fun floor(a: Double): Double = kotlin.math.floor(a)
    override inline fun ceil(a: Double): Double = kotlin.math.ceil(a)
    override inline fun compareTo(a: Double, b: Double): Int = a.compareTo(b)
}

@Suppress("NOTHING_TO_INLINE", "OVERRIDE_BY_INLINE")
object BigIntegerMonoid : Monoid<BigInteger> {
    override inline fun zero(): BigInteger = BigInteger.ZERO
    override inline fun one(): BigInteger = BigInteger.ONE
    override inline fun plus(a: BigInteger, b: BigInteger): BigInteger = a + b
    override inline fun unaryMinus(a: BigInteger): BigInteger = -a
    override inline fun minus(a: BigInteger, b: BigInteger): BigInteger = a - b
    override inline fun times(a: BigInteger, b: BigInteger): BigInteger = a * b
    override inline fun div(a: BigInteger, b: BigInteger): BigInteger = a / b
    override inline fun pow(a: BigInteger, b: Int): Double = a.pow(b).toDouble()
    override inline fun sqrt(a: BigInteger): BigInteger = a.sqrt()
    override inline fun abs(a: BigInteger): BigInteger = a.abs()
    override inline fun floor(a: BigInteger): BigInteger = a
    override inline fun ceil(a: BigInteger): BigInteger = a
    override inline fun compareTo(a: BigInteger, b: BigInteger): Int = a.compareTo(b)
}

@Suppress("NOTHING_TO_INLINE", "OVERRIDE_BY_INLINE")
object BigDecimalMonoid : Monoid<BigDecimal> {
    override inline fun zero(): BigDecimal = BigDecimal.ZERO
    override inline fun one(): BigDecimal = BigDecimal.ONE
    override inline fun plus(a: BigDecimal, b: BigDecimal): BigDecimal = a + b
    override inline fun unaryMinus(a: BigDecimal): BigDecimal = -a
    override inline fun minus(a: BigDecimal, b: BigDecimal): BigDecimal = a - b
    override inline fun times(a: BigDecimal, b: BigDecimal): BigDecimal = a * b
    override inline fun div(a: BigDecimal, b: BigDecimal): BigDecimal = a.divide(b, MathContext.DECIMAL128)
    override inline fun pow(a: BigDecimal, b: Int): Double = a.pow(b).toDouble()
    override inline fun sqrt(a: BigDecimal): BigDecimal = a.sqrt(MathContext.DECIMAL128)
    override inline fun abs(a: BigDecimal): BigDecimal = a.abs()
    override inline fun floor(a: BigDecimal): BigDecimal = a.setScale(0, RoundingMode.FLOOR)
    override inline fun ceil(a: BigDecimal): BigDecimal = a.setScale(0, RoundingMode.CEILING)
    override inline fun compareTo(a: BigDecimal, b: BigDecimal): Int = a.compareTo(b)
}
