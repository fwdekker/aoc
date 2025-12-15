package com.fwdekker.std.maths

import com.fwdekker.std.collections.swapAt
import com.fwdekker.std.foldSelf
import com.fwdekker.std.toDigits
import java.math.BigInteger


/**
 * Returns the (falling) factorial of [this] number (to [that]).
 *
 * The factorial of `n` (denoted `n!`) is `n * (n - 1) * (n - 2) * ... * 1`.
 *
 * The falling factorial of `n` to `k` is `n! / (n - k)!`, which is `n * (n - 1) * (n - 2) * ... * (n - (k - 1))`. If we
 * have `n = k` (or `n - 1 = k`), then this evaluates to simply `n!`.
 *
 * If either [this] or [that] is non-positive, the output is one.
 */
fun Int.factorial(that: Int = this): BigInteger = toBigInteger().factorial(that.toBigInteger())

fun Long.factorial(that: Long = this): BigInteger = toBigInteger().factorial(that.toBigInteger())

fun BigInteger.factorial(that: BigInteger = this): BigInteger = factorial(this, that)

private tailrec fun factorial(number: BigInteger, steps: BigInteger, acc: BigInteger = BigInteger.ONE): BigInteger =
    if (number < BigInteger.ZERO) BigInteger.ONE
    else if (number == BigInteger.ZERO || steps <= BigInteger.ZERO) acc
    else factorial(number - BigInteger.ONE, steps.dec(), acc * number)

/**
 * Returns the binomial coefficient of [this] and [k].
 */
fun Int.choose(k: Int): BigInteger = toBigInteger().choose(k.toBigInteger())

fun Long.choose(k: Long): BigInteger = toBigInteger().choose(k.toBigInteger())

fun BigInteger.choose(k: BigInteger): BigInteger {
    require(this >= BigInteger.ZERO && k >= BigInteger.ZERO) { "Both inputs must be non-negative." }

    val overlap = min(BigInteger.ZERO, k * BigInteger.TWO - this)

    return if (k > this) BigInteger.ZERO
    else if (k == this) BigInteger.ONE
    else this.factorial(k - overlap) / (k - overlap).factorial()
}


/**
 * Returns all possible combinations of elements in [this] and [that].
 *
 * To perform the Cartesian product of something with itself, see [combinations].
 */
fun <T, U> Iterable<T>.cartesian(that: Iterable<U>): Sequence<Pair<T, U>> =
    asSequence().cartesian(that)

fun <T, U> Sequence<T>.cartesian(that: Iterable<U>): Sequence<Pair<T, U>> =
    flatMap { a -> that.map { b -> Pair(a, b) } }

/**
 * Returns all possible combinations of elements in [this], [that], and [other].
 *
 * To perform the Cartesian product of something with itself, see [combinations].
 */
fun <T, U, V> Iterable<T>.cartesian(that: Iterable<U>, other: Iterable<V>): Sequence<Triple<T, U, V>> =
    asSequence().cartesian(that, other)

fun <T, U, V> Sequence<T>.cartesian(that: Iterable<U>, other: Iterable<V>): Sequence<Triple<T, U, V>> =
    flatMap { a -> that.flatMap { b -> other.map { c -> Triple(a, b, c) } } }

/**
 * Returns all possible ways of choosing exactly [amount] elements from [this], with repetition.
 *
 * If [amount] is `2`, this is the same as doing [cartesian] on [this] with itself.
 */
fun <T> Sequence<T>.combinations(amount: Int): Sequence<List<T>> {
    require(amount >= 0) { "Cannot choose $amount elements from a list." }

    return if (amount == 0) emptySequence()
    else map { listOf(it) }.foldSelf(amount - 1) { all -> all.flatMap { combo -> map { combo + it } } }
}

fun <T> Iterable<T>.combinations(amount: Int): Sequence<List<T>> = asSequence().combinations(amount)

/**
 * Returns all possible permutations of [this] collection's elements.
 *
 * Uses the Steinhaus–Johnson–Trotter algorithm, with Even's speedup.
 */
fun <T> List<T>.permutations(): Sequence<List<T>> =
    if (isEmpty()) emptySequence()
    else sequence {
        yield(toList())

        val permutation = indices.toMutableList()
        val directions = indices.map { if (it == 0) 0 else -1 }.toMutableList()

        while (true) {
            val swapFrom = indices.filter { directions[it] != 0 }.maxByOrNull { permutation[it] } ?: return@sequence
            val swapTo = swapFrom + directions[swapFrom]
            val swapAfter = swapTo + directions[swapFrom]
            val swapValue = permutation[swapFrom]

            permutation.swapAt(swapFrom, swapTo)
            directions.swapAt(swapFrom, swapTo)
            if (swapTo == 0 || swapTo == size - 1 || permutation[swapAfter] > swapValue) directions[swapTo] = 0
            indices.filter { permutation[it] > swapValue }.forEach { directions[it] = if (it < swapTo) 1 else -1 }

            yield(permutation.map { this@permutations[it] })
        }
    }

fun <T> Iterable<T>.permutations(): Sequence<List<T>> = toList().permutations()

/**
 * Returns all possible ways of partitioning [this] into [partitions] partitions.
 *
 * Each element in the sequence is a list. Each list has a size equal to the size of [this], with each element an
 * integer in the range `0..<partitions`, mapping the elements of [this] to the assigned partitions.
 */
fun <T> List<T>.orderedPartitions(partitions: Int): Sequence<List<Int>> =
    BigInteger.ZERO.rangeUntil(partitions.toBigInteger().pow(size))
        .asSequence()
        .map { partition -> partition.toString(partitions).padStart(size, '0').toDigits() }

fun <T> Collection<T>.orderedPartitions(partitions: Int): Sequence<List<Int>> = toList().orderedPartitions(partitions)

/**
 * Returns all (not necessarily proper) subsets of [this], except those outside the range `minSize..maxSize`.
 */
fun <T> List<T>.powerSet(minSize: Int = 0, maxSize: Int = this.size): Sequence<List<T>> {
    require(minSize <= maxSize) { "Minimum size must be no more than maximum size." }
    require(minSize >= 0) { "Minimum size must be non-negative." }
    require(maxSize <= size) { "Maximum size must be at most collection size." }

    return orderedPartitions(2)
        .map { it.withIndex().filter { (_, bit) -> bit == 1 }.map { (idx, _) -> this[idx] } }
        .filter { it.size in minSize..maxSize }
}

fun <T> Collection<T>.powerSet(minSize: Int = 0, maxSize: Int = this.size): Sequence<List<T>> =
    toList().powerSet(minSize, maxSize)

/**
 * Returns all unique pairs of elements. Equivalent to [powerSet] with exact size 2, but faster.
 */
fun <T> Collection<T>.allPairs(): Sequence<Pair<T, T>> =
    asSequence().flatMapIndexed { idx, a -> asSequence().drop(idx + 1).map { b -> a to b } }


/**
 * Returns the number of occurrences for each element.
 */
fun <T> Collection<T>.histogram(): Map<T, Int> = groupBy { it }.mapValues { it.value.size }
