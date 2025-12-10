@file:Suppress("unused")

package com.fwdekker.std.maths

import com.fwdekker.std.collections.map
import java.util.BitSet
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.sqrt
import kotlin.streams.toList


/**
 * Returns the integer-rounded square root.
 */
fun floorSqrt(value: Int): Int = value.toBigInteger().sqrt().toInt()

fun floorSqrt(value: Long): Long = value.toBigInteger().sqrt().toLong()

/**
 * Returns `true` if and only if [this] is exactly the square of an integer number.
 */
fun Int.isPerfectSquare(): Boolean = toLong().isPerfectSquare()

fun Long.isPerfectSquare(): Boolean = floorSqrt(this).let { it * it } == this


/**
 * Returns the greatest common divisor of [a] and [b].
 *
 * Note that:
 * * If either value is zero, the absolute of the other value is returned.
 * * If both values are zero, then the output is zero.
 * * The output is independent of the sign of the inputs.
 */
fun gcd(a: Int, b: Int): Int = gcd(a.toLong(), b.toLong()).toInt()

tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) abs(a) else gcd(b, a % b)

fun Pair<Int, Int>.gcd(): Int = gcd(first, second)

fun Pair<Long, Long>.gcd(): Long = gcd(first, second)

/**
 * Returns the greatest common divisor of all elements in [this] list.
 *
 * Note that if the list is empty, then the output is zero.
 *
 * @see gcd
 */
fun Iterable<Int>.gcd(): Int = toLongs().gcd().toInt()

fun Iterable<Long>.gcd(): Long =
    toList().let {
        if (it.isEmpty()) 0L
        else reduce(::gcd)
    }

/**
 * Returns the least common multiplier of [a] and [b].
 *
 * An exception is thrown if (and only if) both [a] and [b] are zero.
 */
fun lcm(a: Int, b: Int): Int = lcm(a.toLong(), b.toLong()).toIntExact()

fun lcm(a: Long, b: Long): Long {
    require(a != 0L || b != 0L) { "Cannot compute lcm of 0 and 0." }
    return abs(a * (b / gcd(a, b)))
}

fun Pair<Int, Int>.lcm(): Int = lcm(first, second)

fun Pair<Long, Long>.lcm(): Long = lcm(first, second)

/**
 * Returns the least common multiple of all elements in [this] list.
 *
 * Note that:
 * * If the list is empty or contains only zeroes, an exception is thrown.
 * * Otherwise, if the list contains at least one zero, the output is zero.
 *
 * @see lcm
 */
fun Iterable<Int>.lcm(): Int = toLongs().lcm().toIntExact()

fun Iterable<Long>.lcm(): Long =
    toList().let { list ->
        require(list.isNotEmpty()) { "Cannot compute lcm of empty list." }

        val zeros = count { it == 0L }
        require(zeros < list.size) { "Cannot compute lcm of only 0s." }

        if (zeros > 0) return 0L
        else filterNot { it == 0L }.reduce(::lcm)
    }

/**
 * Simplifies the ratio expressed by this pair by dividing both elements by their [gcd].
 */
@JvmName("intPairSimplifyRatio")
fun Pair<Int, Int>.simplifyRatio() = gcd().let { gcd -> map { it / gcd } }

@JvmName("longPairSimplifyRatio")
fun Pair<Long, Long>.simplifyRatio() = gcd().let { gcd -> map { it / gcd } }


/**
 * Returns the prime factors of [this].
 */
fun Int.factorize(): Sequence<Int> = toLong().factorize().toInts()

fun Long.factorize(): Sequence<Long> =
    sequence {
        var remaining = this@factorize
        primes
            .takeWhile { remaining > 1L }
            .forEach {
                while (remaining % it == 0L) {
                    yield(it)
                    remaining /= it
                }
            }
    }

/**
 * Returns the prime factors of [this] in a map with the number of occurrences of each factor.
 */
fun Int.factorizeGroups(): Map<Int, Int> = toLong().factorizeGroups().mapKeys { (k, _) -> k.toInt() }

fun Long.factorizeGroups(): Map<Long, Int> {
    val factors = mutableMapOf<Long, Int>()

    var remaining = this
    primes
        .takeWhile { remaining > 1L }
        .forEach { factor ->
            while (remaining % factor == 0L) {
                factors.merge(factor, 1, Int::plus)
                remaining /= factor
            }
        }

    return factors
}

/**
 * Returns all (positive) natural numbers that divide [this], excluding [this].
 */
fun Int.divisors(): Set<Int> = toLong().divisors().toInts().toSet()

fun Long.divisors(): Set<Long> =
    if (this <= 1L) emptySet()
    else (listOf(1L) + factorize()).powerSet(minSize = 1).map { it.product() }.sorted().toSet().minus(this)

/**
 * Returns the number of [divisors]. More efficient than first calling [divisors] and then `size`!
 */
fun Int.divisorsCount(): Int = toLong().divisorsCount().toInt()

fun Long.divisorsCount(): Long = if (this <= 1L) 0L else factorizeGroups().values.productOf { it + 1 } - 1

/**
 * Returns the sum of [divisors]. More efficient than first calling [divisors] and then `sum`!
 */
fun Int.divisorsSum(): Int = toLong().divisorsSum().toIntExact()

fun Long.divisorsSum(): Long =
    if (this <= 1L) 0L
    else factorizeGroups()
        .also { if (this in it) return 1L }
        .map { (factor, occurrences) -> generateSequence(1L) { (it * factor) + 1L }.drop(occurrences).first() }
        .product() - this


/**
 * Returns `true` if and only if [this] is a prime number.
 */
fun Int.isPrime(): Boolean = toLong().isPrime()

fun Long.isPrime(): Boolean {
    if (this < 2L) return false
    if (this == 2L) return true
    if (this % 2L == 0L) return false

    val max = floor(sqrt(this.toDouble())).toLong()
    for (factor in 3L..max step 2)
        if (this % factor == 0L) return false

    return true
}


/**
 * An infinite sequence of primes, starting at 2.
 *
 * Results are cached for your performance.
 */
val primes: CachedSequence<Long> =
    sequence {
        yield(2L)
        yieldAll(
            generateSequence(3L) {
                var at = it + 2
                while (!at.isPrime()) at += 2
                at
            }
        )
    }.cached()

/**
 * Calculates all primes up to and including [n], stores these into the cached [primes], and then returns [primes].
 *
 * Uses the (slightly optimised) Sieve of Eratosthenes.
 *
 * This method is considerably faster only starting at around [n] of at least `100_000`.
 */
// TODO: Use the Sieve of Atkin instead.
fun seededPrimes(n: Int): CachedSequence<Long> {
    val sieve = BitSet(n + 1).apply { flip(2, n) }

    (2..floorSqrt(n))
        .filter { sieve[it] }
        .forEach { number -> (number.powInt(2)..<n).step(number).forEach { sieve[it] = false } }

    primes.insert(sieve.stream().toList().toLongs())
    return primes
}
