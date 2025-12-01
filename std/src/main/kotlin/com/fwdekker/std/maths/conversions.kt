@file:Suppress("unused")
package com.fwdekker.std.maths

import com.fwdekker.std.collections.map
import java.math.BigDecimal
import java.math.BigInteger


/**
 * Converts [this] to an [Int].
 */
fun Boolean.toInt(): Int = if (this) 1 else 0

/**
 * Converts [this] to a [Long].
 */
fun Boolean.toLong(): Long = if (this) 1L else 0L


/**
 * Converts [this] to an [Int], or throws an exception if over- or underflow would occur.
 *
 * Use the regular [toInt] when you know that no over- or underflow can realistically occur.
 */
fun Long.toIntExact(): Int = Math.toIntExact(this)

fun BigInteger.toIntExact(): Int {
    if (this !in Int.MIN_VALUE.toBigInteger()..Int.MAX_VALUE.toBigInteger())
        throw ArithmeticException("Integer overflow.")

    return toInt()
}

/**
 * Converts [this] to a [Long], or throws an exception if over- or underflow would occur.
 *
 * Use the regular [toLong] when you know that no over- or underflow can realistically occur.
 */
fun BigInteger.toLongExact(): Long {
    if (this !in Long.MIN_VALUE.toBigInteger()..Long.MAX_VALUE.toBigInteger())
        throw ArithmeticException("Integer overflow.")

    return toLong()
}


/**
 * Converts all numbers to [Int]s.
 */
fun Pair<Number, Number>.toInts(): Pair<Int, Int> = map { it.toInt() }

fun Triple<Number, Number, Number>.toInts(): Triple<Int, Int, Int> = map { it.toInt() }

@JvmName("numbersToInts")
fun Sequence<Number>.toInts(): Sequence<Int> = map { it.toInt() }

@JvmName("numbersToInts")
fun Iterable<Number>.toInts(): List<Int> = map { it.toInt() }

@JvmName("numberPairsToInts")
fun Sequence<Pair<Number, Number>>.toInts(): Sequence<Pair<Int, Int>> = map { it.toInts() }

@JvmName("numberPairsToInts")
fun Iterable<Pair<Number, Number>>.toInts(): List<Pair<Int, Int>> = map { it.toInts() }

@JvmName("numberTriplesToInts")
fun Sequence<Triple<Number, Number, Number>>.toInts(): Sequence<Triple<Int, Int, Int>> = map { it.toInts() }

@JvmName("numberTriplesToInts")
fun Iterable<Triple<Number, Number, Number>>.toInts(): List<Triple<Int, Int, Int>> = map { it.toInts() }


/**
 * Converts all numbers to [Long]s.
 */
fun Pair<Number, Number>.toLongs(): Pair<Long, Long> = map { it.toLong() }

fun Triple<Number, Number, Number>.toLongs(): Triple<Long, Long, Long> = map { it.toLong() }

@JvmName("numbersToLongs")
fun Sequence<Number>.toLongs(): Sequence<Long> = map { it.toLong() }

@JvmName("numbersToLongs")
fun Iterable<Number>.toLongs(): List<Long> = map { it.toLong() }

@JvmName("numberPairsToLongs")
fun Sequence<Pair<Number, Number>>.toLongs(): Sequence<Pair<Long, Long>> = map { it.toLongs() }

@JvmName("numberPairsToLongs")
fun Iterable<Pair<Number, Number>>.toLongs(): List<Pair<Long, Long>> = map { it.toLongs() }

@JvmName("numberTriplesToLongs")
fun Sequence<Triple<Number, Number, Number>>.toLongs(): Sequence<Triple<Long, Long, Long>> = map { it.toLongs() }

@JvmName("numberTriplesToLongs")
fun Iterable<Triple<Number, Number, Number>>.toLongs(): List<Triple<Long, Long, Long>> = map { it.toLongs() }


/**
 * Converts all numbers to [BigInteger]s.
 */
private fun Number.toBigInt(): BigInteger = toLong().toBigInteger()

fun Pair<Number, Number>.toBigIntegers(): Pair<BigInteger, BigInteger> = map { it.toBigInt() }

fun Triple<Number, Number, Number>.toBigIntegers(): Triple<BigInteger, BigInteger, BigInteger> = map { it.toBigInt() }

@JvmName("numbersToBigIntegers")
fun Sequence<Number>.toBigIntegers(): Sequence<BigInteger> = map { it.toBigInt() }

@JvmName("numbersToBigIntegers")
fun Iterable<Number>.toBigIntegers(): List<BigInteger> = map { it.toBigInt() }

@JvmName("numberPairsToBigIntegers")
fun Sequence<Pair<Number, Number>>.toBigIntegers(): Sequence<Pair<BigInteger, BigInteger>> = map { it.toBigIntegers() }

@JvmName("numberPairsToBigIntegers")
fun Iterable<Pair<Number, Number>>.toBigIntegers(): List<Pair<BigInteger, BigInteger>> = map { it.toBigIntegers() }

@JvmName("numberTriplesToBigIntegers")
fun Sequence<Triple<Number, Number, Number>>.toBigInteger(): Sequence<Triple<BigInteger, BigInteger, BigInteger>> =
    map { it.toBigIntegers() }

@JvmName("numberTriplesToBigIntegers")
fun Iterable<Triple<Number, Number, Number>>.toBigInteger(): List<Triple<BigInteger, BigInteger, BigInteger>> =
    map { it.toBigIntegers() }


/**
 * Converts all numbers to [BigDecimal]s.
 */
private fun Number.toBigDec(): BigDecimal = toLong().toBigDecimal()

fun Pair<Number, Number>.toBigDecimals(): Pair<BigDecimal, BigDecimal> = map { it.toBigDec() }

fun Triple<Number, Number, Number>.toBigDecimals(): Triple<BigDecimal, BigDecimal, BigDecimal> = map { it.toBigDec() }

@JvmName("numbersToBigDecimals")
fun Sequence<Number>.toBigDecimals(): Sequence<BigDecimal> = map { it.toBigDec() }

@JvmName("numbersToBigDecimals")
fun Iterable<Number>.toBigDecimals(): List<BigDecimal> = map { it.toBigDec() }

@JvmName("numberPairsToBigDecimals")
fun Sequence<Pair<Number, Number>>.toBigDecimals(): Sequence<Pair<BigDecimal, BigDecimal>> = map { it.toBigDecimals() }

@JvmName("numberPairsToBigDecimals")
fun Iterable<Pair<Number, Number>>.toBigDecimals(): List<Pair<BigDecimal, BigDecimal>> = map { it.toBigDecimals() }

@JvmName("numberTriplesToBigDecimals")
fun Sequence<Triple<Number, Number, Number>>.toBigDecimal(): Sequence<Triple<BigDecimal, BigDecimal, BigDecimal>> =
    map { it.toBigDecimals() }

@JvmName("numberTriplesToBigDecimals")
fun Iterable<Triple<Number, Number, Number>>.toBigDecimal(): List<Triple<BigDecimal, BigDecimal, BigDecimal>> =
    map { it.toBigDecimals() }
