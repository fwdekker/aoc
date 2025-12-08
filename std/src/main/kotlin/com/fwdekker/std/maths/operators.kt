@file:Suppress("unused")
package com.fwdekker.std.maths

import com.fwdekker.std.collections.map
import com.fwdekker.std.collections.foldSum
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.experimental.ExperimentalTypeInference
import kotlin.time.Duration


/**
 * Calculates [this] modulo [mod] such that if [this] is `-1` the output is `mod - 1`.
 */
fun Int.wrapMod(mod: Int): Int = ((this % mod) + mod) % mod

fun Long.wrapMod(mod: Int): Int = (((this % mod) + mod) % mod).toInt()

fun Long.wrapMod(mod: Long): Long = ((this % mod) + mod) % mod


/**
 * Takes the component-wise addition of the pairs.
 */
@JvmName("intPairPlus")
operator fun Pair<Int, Int>.plus(that: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.first + that.first, this.second + that.second)

@JvmName("longPairPlus")
operator fun Pair<Long, Long>.plus(that: Pair<Long, Long>): Pair<Long, Long> =
    Pair(this.first + that.first, this.second + that.second)

@JvmName("bigIntPairPlus")
operator fun Pair<BigInteger, BigInteger>.plus(that: Pair<BigInteger, BigInteger>): Pair<BigInteger, BigInteger> =
    Pair(this.first + that.first, this.second + that.second)

@JvmName("bigDecPairPlus")
operator fun Pair<BigDecimal, BigDecimal>.plus(that: Pair<BigDecimal, BigDecimal>): Pair<BigDecimal, BigDecimal> =
    Pair(this.first + that.first, this.second + that.second)

/**
 * Takes the component-wise subtraction of the pairs.
 */
@JvmName("intPairMinus")
operator fun Pair<Int, Int>.minus(that: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.first - that.first, this.second - that.second)

@JvmName("longPairMinus")
operator fun Pair<Long, Long>.minus(that: Pair<Long, Long>): Pair<Long, Long> =
    Pair(this.first - that.first, this.second - that.second)

@JvmName("bigIntPairMinus")
operator fun Pair<BigInteger, BigInteger>.minus(that: Pair<BigInteger, BigInteger>): Pair<BigInteger, BigInteger> =
    Pair(this.first - that.first, this.second - that.second)

@JvmName("bigDecPairMinus")
operator fun Pair<BigDecimal, BigDecimal>.minus(that: Pair<BigDecimal, BigDecimal>): Pair<BigDecimal, BigDecimal> =
    Pair(this.first - that.first, this.second - that.second)

/**
 * Takes the sum of all elements.
 */
@JvmName("bigIntSum")
fun Sequence<BigInteger>.sum(): BigInteger = fold(BigInteger.ZERO) { acc, it -> acc + it }

@JvmName("bigIntSum")
fun Iterable<BigInteger>.sum(): BigInteger = asSequence().sum()

@JvmName("bigDecSum")
fun Sequence<BigDecimal>.sum(): BigDecimal = fold(BigDecimal.ZERO) { acc, it -> acc + it }

@JvmName("bigDecSum")
fun Iterable<BigDecimal>.sum(): BigDecimal = asSequence().sum()

@JvmName("durationSum")
fun Sequence<Duration>.sum(): Duration = fold(Duration.ZERO) { acc, it -> acc + it }

@JvmName("durationSum")
fun Iterable<Duration>.sum(): Duration = fold(Duration.ZERO) { acc, it -> acc + it }

/**
 * Takes the component-wise sum of all elements.
 */
@JvmName("intPairSum")
fun Sequence<Pair<Int, Int>>.sum(): Pair<Int, Int> = fold(Pair(0, 0)) { acc, it -> acc + it }

@JvmName("intPairSum")
fun Iterable<Pair<Int, Int>>.sum(): Pair<Int, Int> = asSequence().sum()

@JvmName("longPairSum")
fun Sequence<Pair<Long, Long>>.sum(): Pair<Long, Long> = fold(Pair(0L, 0L)) { acc, it -> acc + it }

@JvmName("longPairSum")
fun Iterable<Pair<Long, Long>>.sum(): Pair<Long, Long> = asSequence().sum()

@JvmName("bigIntPairSum")
fun Sequence<Pair<BigInteger, BigInteger>>.sum(): Pair<BigInteger, BigInteger> =
    fold(Pair(BigInteger.ZERO, BigInteger.ZERO)) { acc, it -> acc + it }

@JvmName("bigIntPairSum")
fun Iterable<Pair<BigInteger, BigInteger>>.sum(): Pair<BigInteger, BigInteger> = asSequence().sum()

@JvmName("bigDecPairSum")
fun Sequence<Pair<BigDecimal, BigDecimal>>.sum(): Pair<BigDecimal, BigDecimal> =
    fold(Pair(BigDecimal.ZERO, BigDecimal.ZERO)) { acc, it -> acc + it }

@JvmName("bigDecPairSum")
fun Iterable<Pair<BigDecimal, BigDecimal>>.sum(): Pair<BigDecimal, BigDecimal> = asSequence().sum()

/**
 * Shorthand for invoking [map] and then [foldSum].
 */
@JvmName("intSumOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Collection<T>.sumOf(transform: (T) -> Int): Int = map(transform).sum()

@JvmName("longSumOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Collection<T>.sumOf(transform: (T) -> Long): Long = map(transform).sum()

@JvmName("bigIntSumOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Collection<T>.sumOf(transform: (T) -> BigInteger): BigInteger = map(transform).sum()

@JvmName("durationIntSumOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Collection<T>.sumOf(transform: (T) -> Duration): Duration = map(transform).sum()

/**
 * Shorthand for invoking [withIndex] and then [sumOf].
 */
@JvmName("intSumOfIndexed")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Collection<T>.sumOfIndexed(transform: (Int, T) -> Int): Int =
    withIndex().sumOf { (idx, element) -> transform(idx, element) }

@JvmName("longSumOfIndexed")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Collection<T>.sumOfIndexed(transform: (Int, T) -> Long): Long =
    withIndex().sumOf { (idx, element) -> transform(idx, element) }

@JvmName("bigIntSumOfIndexed")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Collection<T>.sumOfIndexed(transform: (Int, T) -> BigInteger): BigInteger =
    withIndex().sumOf { (idx, element) -> transform(idx, element) }

/**
 * Shorthand for invoking [filter] and then [foldSum].
 */
fun Sequence<Int>.sumIf(predicate: (Int) -> Boolean): Int = filter(predicate).sum()

fun Iterable<Int>.sumIf(predicate: (Int) -> Boolean): Int = filter(predicate).sum()

fun Sequence<Long>.sumIf(predicate: (Long) -> Boolean): Long = filter(predicate).sum()

fun Iterable<Long>.sumIf(predicate: (Long) -> Boolean): Long = filter(predicate).sum()

fun Sequence<BigInteger>.sumIf(predicate: (BigInteger) -> Boolean): BigInteger = filter(predicate).sum()

fun Iterable<BigInteger>.sumIf(predicate: (BigInteger) -> Boolean): BigInteger = filter(predicate).sum()


/**
 * Takes the component-wise multiplication of the pairs.
 */
@JvmName("intPairTimes")
operator fun Pair<Int, Int>.times(that: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.first * that.first, this.second * that.second)

@JvmName("longPairTimes")
operator fun Pair<Long, Long>.times(that: Pair<Long, Long>): Pair<Long, Long> =
    Pair(this.first * that.first, this.second * that.second)

@JvmName("bigIntPairTimes")
operator fun Pair<BigInteger, BigInteger>.times(that: Pair<BigInteger, BigInteger>): Pair<BigInteger, BigInteger> =
    Pair(this.first * that.first, this.second * that.second)

@JvmName("bigDecPairTimes")
operator fun Pair<BigDecimal, BigDecimal>.times(that: Pair<BigDecimal, BigDecimal>): Pair<BigDecimal, BigDecimal> =
    Pair(this.first * that.first, this.second * that.second)

/**
 * Takes the component-wise division of the pairs.
 */
@JvmName("intPairDiv")
operator fun Pair<Int, Int>.div(that: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.first / that.first, this.second / that.second)

@JvmName("longPairDiv")
operator fun Pair<Long, Long>.div(that: Pair<Long, Long>): Pair<Long, Long> =
    Pair(this.first / that.first, this.second / that.second)

@JvmName("bigIntPairDiv")
operator fun Pair<BigInteger, BigInteger>.div(that: Pair<BigInteger, BigInteger>): Pair<BigInteger, BigInteger> =
    Pair(this.first / that.first, this.second / that.second)

@JvmName("bigDecPairDiv")
operator fun Pair<BigDecimal, BigDecimal>.div(that: Pair<BigDecimal, BigDecimal>): Pair<BigDecimal, BigDecimal> =
    Pair(this.first / that.first, this.second / that.second)

/**
 * Takes the product of all elements.
 */
@JvmName("intProduct")
fun Sequence<Int>.product(): Long = toLongs().product()

@JvmName("intProduct")
fun Iterable<Int>.product(): Long = toLongs().product()

@JvmName("longProduct")
fun Sequence<Long>.product(): Long = reduce(Long::times)

@JvmName("longProduct")
fun Iterable<Long>.product(): Long = asSequence().product()

@JvmName("bigIntProduct")
fun Sequence<BigInteger>.product(): BigInteger = reduce(BigInteger::times)

@JvmName("bigIntProduct")
fun Iterable<BigInteger>.product(): BigInteger = asSequence().product()

@JvmName("bigDecProduct")
fun Sequence<BigDecimal>.product(): BigDecimal = reduce(BigDecimal::times)

@JvmName("bigDecProduct")
fun Iterable<BigDecimal>.product(): BigDecimal = asSequence().product()

/**
 * Takes the pair-wise product of all elements.
 */
@JvmName("intProduct")
fun Sequence<Pair<Int, Int>>.product(): Pair<Long, Long> = toLongs().product()

@JvmName("intProduct")
fun Iterable<Pair<Int, Int>>.product(): Pair<Long, Long> = asSequence().product()

@JvmName("longProduct")
fun Sequence<Pair<Long, Long>>.product(): Pair<Long, Long> = fold(Pair(1L, 1L)) { acc, it -> acc * it }

@JvmName("longProduct")
fun Iterable<Pair<Long, Long>>.product(): Pair<Long, Long> = asSequence().product()

@JvmName("bigIntProduct")
fun Sequence<Pair<BigInteger, BigInteger>>.product(): Pair<BigInteger, BigInteger> =
    fold(Pair(BigInteger.ONE, BigInteger.ONE)) { acc, it -> acc * it }

@JvmName("bigIntProduct")
fun Iterable<Pair<BigInteger, BigInteger>>.product(): Pair<BigInteger, BigInteger> = asSequence().product()

@JvmName("bigDecProduct")
fun Sequence<Pair<BigDecimal, BigDecimal>>.product(): Pair<BigDecimal, BigDecimal> =
    fold(Pair(BigDecimal.ONE, BigDecimal.ONE)) { acc, it -> acc * it }

@JvmName("bigDecProduct")
fun Iterable<Pair<BigDecimal, BigDecimal>>.product(): Pair<BigDecimal, BigDecimal> = asSequence().product()

/**
 * Shorthand for invoking [map] and then [product].
 */
@JvmName("intProductOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Sequence<T>.productOf(transform: (T) -> Int): Long = map(transform).product()

@JvmName("intProductOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Iterable<T>.productOf(transform: (T) -> Int): Long = map(transform).product()

@JvmName("longProductOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Sequence<T>.productOf(transform: (T) -> Long): Long = map(transform).product()

@JvmName("longProductOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Iterable<T>.productOf(transform: (T) -> Long): Long = map(transform).product()

@JvmName("bigIntProductOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Sequence<T>.productOf(transform: (T) -> BigInteger): BigInteger = map(transform).product()

@JvmName("bigIntProductOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Iterable<T>.productOf(transform: (T) -> BigInteger): BigInteger = map(transform).product()

@JvmName("bigDecProductOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Sequence<T>.productOf(transform: (T) -> BigDecimal): BigDecimal = map(transform).product()

@JvmName("bigDecProductOf")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <T> Iterable<T>.productOf(transform: (T) -> BigDecimal): BigDecimal = map(transform).product()


/**
 * Calculates [this] to the power of some non-negative [exponent].
 */
@Deprecated("Super duper slow! Rename this to `powExact` or `powSafe` or sth, and add `pow` which can fail.") // TODO
fun Int.pow(exponent: Int): Int = toBigInteger().pow(exponent).toIntExact()

@Deprecated("Super duper slow! Rename this to `powExact` or `powSafe` or sth, and add `pow` which can fail.") // TODO
fun Long.pow(exponent: Int): Long {
    require(exponent >= 0) { "Cannot calculate negative exponent on integer number." }

    return toBigInteger().pow(exponent).toLongExact()
}


/**
 * Returns the minimum of [first] and [second].
 */
fun <T : Comparable<T>> min(first: T, second: T): T = if (first <= second) first else second

/**
 * Returns the maximum of [first] and [second].
 */
fun <T : Comparable<T>> max(first: T, second: T): T = if (first >= second) first else second
