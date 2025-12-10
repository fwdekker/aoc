package com.fwdekker.std.maths

import java.math.BigDecimal
import java.math.MathContext
import kotlin.collections.sumOf
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt


/**
 * Returns the L1 norm.
 */
@JvmName("intNorm1")
fun Iterable<Int>.norm1(): Int = sumOf { abs(it) }
@JvmName("longNorm1")
fun Iterable<Long>.norm1(): Long = sumOf { abs(it) }
@JvmName("bigDecimalNorm1")
fun Iterable<BigDecimal>.norm1(): BigDecimal = sumOf { it.abs() }

/**
 * Returns the L2 norm, i.e. the Euclidian norm.
 */
@JvmName("intNorm2")
fun Iterable<Int>.norm2(): Double = sqrt(sumOf { it.toDouble().pow(2) })
@JvmName("longNorm2")
fun Iterable<Long>.norm2(): Double = sqrt(sumOf { it.toDouble().pow(2) })
@JvmName("bigDecimalNorm2")
fun Iterable<BigDecimal>.norm2(): BigDecimal = sumOf { it.pow(2) }.sqrt(MathContext.DECIMAL128)
