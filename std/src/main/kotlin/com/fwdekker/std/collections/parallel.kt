package com.fwdekker.std.collections

import java.util.stream.Stream


/**
 * Returns a parallel stream over [this].
 */
fun <T> Iterable<T>.parallel(): Stream<T> = toList().parallelStream()


/**
 * @see Iterable.max
 */
fun <T : Comparable<T>> Stream<T>.max(): T = maxBy { it }

/**
 * @see Iterable.maxOf
 */
fun <T, R : Comparable<R>> Stream<T>.maxOf(selector: (T) -> R): R = map(selector).max()

/**
 * @see Iterable.maxBy
 */
fun <T, R : Comparable<R>> Stream<T>.maxBy(selector: (T) -> R): T =
    max { o1, o2 -> selector(o1).compareTo(selector(o2)) }.get()



