package com.fwdekker.std.maths

import com.fwdekker.std.maths.vector.DoubleMonoid
import com.fwdekker.std.maths.vector.Monoid
import com.fwdekker.std.maths.vector.sumOf


/**
 * Returns the L1 norm.
 */
context(monoid: Monoid<N>)
fun <N> Iterable<N>.norm1(): N = sumOf { monoid.abs(it) }

context(monoid: Monoid<N>)
fun <N> Pair<N, N>.norm1(): N = monoid.plus(monoid.abs(first), monoid.abs(second))

context(monoid: Monoid<N>)
fun <N> Triple<N, N, N>.norm1(): N = monoid.plus(monoid.plus(monoid.abs(first), monoid.abs(second)), monoid.abs(third))

/**
 * Returns the L2 norm, i.e. the Euclidian norm.
 */
context(monoid: Monoid<N>)
fun <N> Iterable<N>.norm2(): Double = with(DoubleMonoid) { sqrt(sumOf { monoid.pow(it, 2) }) }

context(monoid: Monoid<N>)
fun <N> Pair<N, N>.norm2(): Double =
    DoubleMonoid.plus(monoid.pow(first, 2), monoid.pow(second, 2))

context(monoid: Monoid<N>)
fun <N> Triple<N, N, N>.norm2(): Double =
    DoubleMonoid.plus(DoubleMonoid.plus(monoid.pow(first, 2), monoid.pow(second, 2)), monoid.pow(third, 2))


context(monoid: Monoid<N>)
fun <N, T> Iterable<N>.distanceTo(that: Iterable<N>, norm: (Iterable<N>) -> T): T =
    norm(this.zip(that) { a, b -> monoid.minus(a, b) })

context(monoid: Monoid<N>)
fun <N, T> Pair<N, N>.distanceTo(that: Pair<N, N>, norm: (Pair<N, N>) -> T): T =
    norm(Pair(monoid.minus(this.first, that.first), monoid.minus(this.second, that.second)))

context(monoid: Monoid<N>)
fun <N, T> Triple<N, N, N>.distanceTo(that: Triple<N, N, N>, norm: (Triple<N, N, N>) -> T): T =
    norm(
        Triple(
            monoid.minus(this.first, that.first),
            monoid.minus(this.second, that.second),
            monoid.minus(this.third, that.third)
        )
    )
