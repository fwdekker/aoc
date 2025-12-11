package com.fwdekker.std.maths

import kotlin.math.absoluteValue


/**
 * Returns 10 to the [power], using a cache.
 */
fun e10i(power: Int): Int = base10Cache.getOrPut(power) { 10.powLong(power) }.toInt()
fun e10l(power: Int): Long = base10Cache.getOrPut(power) { 10.powLong(power) }
private val base10Cache = mutableMapOf<Int, Long>()


/**
 * `2_147_483_647`, `2147483647`, 10 digits
 */
fun Int.length(): Int =
    if (this < 0) {
        if (this == Integer.MIN_VALUE) 11
        else absoluteValue.length() + 1
    } else {
        if (this < 100_000) {
            if (this < 100) {
                if (this < 10) 1
                else 2
            } else {
                if (this < 1_000) {
                    3
                } else {
                    if (this < 10_000) 4
                    else 5
                }
            }
        } else {
            if (this < 10_000_000) {
                if (this < 1_000_000) 6
                else 7
            } else {
                if (this < 100_000_000) {
                    8
                } else {
                    if (this < 1_000_000_000) 9
                    else 10
                }
            }
        }
    }

/**
 * `9_223_372_036_854_775_807`, `9223372036854775807`, 19 digits
 */
fun Long.length(): Int =
    if (this < 0) {
        if (this == Long.MIN_VALUE) 20
        else absoluteValue.length() + 1
    } else {
        if (this < 1_000_000_000) {
            if (this < 10_000) {
                if (this < 100) {
                    if (this < 10) 1
                    else 2
                } else {
                    if (this < 1_000) 3
                    else 4
                }
            } else {
                if (this < 1_000_000) {
                    if (this < 100_000) 5
                    else 6
                } else {
                    if (this < 10_000_000) {
                        7
                    } else {
                        if (this < 100_000_000) 8
                        else 9
                    }
                }
            }
        } else {
            if (this < 100_000_000_000_000) {
                if (this < 100_000_000_000) {
                    if (this < 10_000_000_000) 10
                    else 11
                } else {
                    if (this < 1000_000_000_000) {
                        12
                    } else {
                        if (this < 10_000_000_000_000) 13
                        else 14
                    }
                }
            } else {
                if (this < 10_000_000_000_000_000) {
                    if (this < 1_000_000_000_000_000) 15
                    else 16
                } else {
                    if (this < 100_000_000_000_000_000) {
                        17
                    } else {
                        if (this < 1_000_000_000_000_000_000) 18
                        else 19
                    }
                }
            }
        }
    }


/**
 * Returns the first [digits] digits of `this`.
 */
fun Int.takeDigits(digits: Int): Int = this / e10i(length() - digits)
fun Long.takeDigits(digits: Int): Long = this / e10l(length() - digits)

/**
 * Returns the last [digits] digits of `this`.
 */
fun Int.takeLastDigits(digits: Int): Int = this % e10i(digits)
fun Long.takeLastDigits(digits: Int): Long = this % e10l(digits)

/**
 * Returns everything except the first [digits].
 */
fun Int.dropDigits(digits: Int): Int = this % e10i(length() - digits)
fun Long.dropDigits(digits: Int): Long = this % e10i(length() - digits)

/**
 * Returns everything except the last [digits].
 */
fun Int.dropLastDigits(digits: Int): Int = this / e10i(digits)
fun Long.dropLastDigits(digits: Int): Long = this / e10i(digits)

/**
 * Concatenates [this] and [that].
 */
fun Int.join(that: Int): Int = (this * e10i(that.length())) + that
fun Long.join(that: Long): Long = (this * e10l(that.length())) + that

/**
 * Concatenates the two numbers.
 */
fun Pair<Int, Int>.join(): Int = first.join(second)
fun Pair<Long, Long>.join(): Long = first.join(second)

/**
 * Splits this "string" into two parts at the given [index], the first part corresponding to taking [index], and the
 * latter corresponding to dropping those [index] digits.
 */
fun Int.splitAtIndex(index: Int): Pair<Int, Int> = e10i(length() - index).let { this / it to this % it }
fun Long.splitAtIndex(index: Int): Pair<Long, Long> = e10l(length() - index).let { this / it to this % it }
