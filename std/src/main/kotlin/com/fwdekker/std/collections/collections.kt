@file:Suppress("unused")

package com.fwdekker.std.collections

import com.fwdekker.std.maths.min
import com.fwdekker.std.maths.wrapMod


/**
 * Returns the character at wrapped [index], so `this.getMod(-1)` is equivalent to `this.last()`.
 */
fun String.getMod(index: Int): Char = this[index.wrapMod(length)]

/**
 * Returns the element at wrapped [index], so `this.getMod(-1)` is equivalent to `this.last()`.
 */
fun <T> List<T>.getMod(index: Int): T = this[index.wrapMod(size)]


/**
 * Returns the left-folded addition of all contained maps; if a collision occurs, only the last insertion is retained.
 */
fun <K, V> Sequence<Map<K, V>>.foldSum(): Map<K, V> = foldSum { _, it -> it }

fun <K, V> Iterable<Map<K, V>>.foldSum(): Map<K, V> = foldSum { _, it -> it }

/**
 * Returns the left-folded addition of all contained maps; if a collision occurs, elements are [combine]d.
 */
fun <K, V> Sequence<Map<K, V>>.foldSum(combine: (V?, V) -> V): Map<K, V> =
    mutableMapOf<K, V>().also { acc -> flatten().forEach { (k, v) -> acc[k] = combine(acc[k], v) } }

fun <K, V> Iterable<Map<K, V>>.foldSum(combine: (V?, V) -> V): Map<K, V> =
    mutableMapOf<K, V>().also { acc -> flatten().forEach { (k, v) -> acc[k] = combine(acc[k], v) } }


/**
 * Swaps the first and second entry.
 */
fun <A, B> Pair<A, B>.swap(): Pair<B, A> = Pair(second, first)

/**
 * Takes the sum of all elements of the tuple.
 */
@JvmName("intSum")
fun Pair<Int, Int>.sum(): Int = first + second

@JvmName("intSum")
fun Triple<Int, Int, Int>.sum(): Int = first + second + third

@JvmName("longSum")
fun Pair<Long, Long>.sum(): Long = first + second

@JvmName("longSum")
fun Triple<Long, Long, Long>.sum(): Long = first + second + third

/**
 * Takes the product of all elements of the tuple.
 */
@JvmName("intProduct")
fun Pair<Int, Int>.product(): Int = first * second

@JvmName("intProduct")
fun Triple<Int, Int, Int>.product(): Int = first * second * third

@JvmName("longProduct")
fun Pair<Long, Long>.product(): Long = first * second

@JvmName("longProduct")
fun Triple<Long, Long, Long>.product(): Long = first * second * third

/**
 * Swaps the entries if the [Pair.first] is smaller than the [Pair.second].
 */
fun <A : Comparable<A>> Pair<A, A>.sorted(): Pair<A, A> =
    if (first > second) swap() else this

fun <A> Pair<A, A>.sorted(comparator: Comparator<A>): Pair<A, A> =
    if (comparator.compare(first, second) > 0) swap() else this

/**
 * Returns the first element of each pair.
 */
@JvmName("pairFirsts")
fun <A, B> Sequence<Pair<A, B>>.firsts(): Sequence<A> = map { it.first }

@JvmName("pairFirsts")
fun <A, B> Iterable<Pair<A, B>>.firsts(): List<A> = map { it.first }

/**
 * Returns the second element of each pair.
 */
@JvmName("pairSeconds")
fun <A, B> Sequence<Pair<A, B>>.seconds(): Sequence<B> = map { it.second }

@JvmName("pairSeconds")
fun <A, B> Iterable<Pair<A, B>>.seconds(): List<B> = map { it.second }

/**
 * Returns the first element of each triple.
 */
@JvmName("tripleFirsts")
fun <A, B, C> Sequence<Triple<A, B, C>>.firsts(): Sequence<A> = map { it.first }

@JvmName("tripleFirsts")
fun <A, B, C> Iterable<Triple<A, B, C>>.firsts(): List<A> = map { it.first }

/**
 * Returns the second element of each triple.
 */
@JvmName("tripleSeconds")
fun <A, B, C> Sequence<Triple<A, B, C>>.seconds(): Sequence<B> = map { it.second }

@JvmName("tripleSeconds")
fun <A, B, C> Iterable<Triple<A, B, C>>.seconds(): List<B> = map { it.second }

/**
 * Returns the second element of each triple.
 */
@JvmName("tripleThirds")
fun <A, B, C> Sequence<Triple<A, B, C>>.thirds(): Sequence<C> = map { it.third }

@JvmName("tripleThirds")
fun <A, B, C> Iterable<Triple<A, B, C>>.thirds(): List<C> = map { it.third }

/**
 * Like [List.all], but for [Pair]s.
 */
fun <A> Pair<A, A>.both(predicate: (A) -> Boolean): Boolean =
    predicate(first) && predicate(second)

/**
 * Like [List.all], but for [Triple]s.
 */
fun <A> Triple<A, A, A>.all(predicate: (A) -> Boolean): Boolean =
    predicate(first) && predicate(second) && predicate(third)

/**
 * Maps both entries using [transform].
 */
fun <A, T> Pair<A, A>.map(transform: (A) -> T): Pair<T, T> = Pair(transform(first), transform(second))

/**
 * Maps all entries using [transform].
 */
fun <A, T> Triple<A, A, A>.map(transform: (A) -> T): Triple<T, T, T> =
    Triple(transform(first), transform(second), transform(third))

/**
 * Maps only the first entry using [transform].
 */
fun <A, B, T> Pair<A, B>.mapFirst(transform: (A) -> T): Pair<T, B> =
    Pair(transform(first), second)

fun <A, B, C, T> Triple<A, B, C>.mapFirst(transform: (A) -> T): Triple<T, B, C> =
    Triple(transform(first), second, third)

/**
 * Maps the first entry in each tuple using [transform].
 */
@JvmName("sequencePairMapFirsts")
fun <A, B, T> Sequence<Pair<A, B>>.mapFirsts(transform: (A) -> T): Sequence<Pair<T, B>> =
    map { it.mapFirst(transform) }

@JvmName("iterablePairMapFirsts")
fun <A, B, T> Iterable<Pair<A, B>>.mapFirsts(transform: (A) -> T): List<Pair<T, B>> =
    map { it.mapFirst(transform) }

@JvmName("sequenceTripleMapFirsts")
fun <A, B, C, T> Sequence<Triple<A, B, C>>.mapFirsts(transform: (A) -> T): Sequence<Triple<T, B, C>> =
    map { it.mapFirst(transform) }

@JvmName("iterableTripleMapFirsts")
fun <A, B, C, T> Iterable<Triple<A, B, C>>.mapFirsts(transform: (A) -> T): List<Triple<T, B, C>> =
    map { it.mapFirst(transform) }

/**
 * Maps only the second entry using [transform].
 */
fun <A, B, T> Pair<A, B>.mapSecond(transform: (B) -> T): Pair<A, T> =
    Pair(first, transform(second))

fun <A, B, C, T> Triple<A, B, C>.mapSecond(transform: (B) -> T): Triple<A, T, C> =
    Triple(first, transform(second), third)

/**
 * Maps the second entry in each tuple using [transform].
 */
@JvmName("sequencePairMapSeconds")
fun <A, B, T> Sequence<Pair<A, B>>.mapSeconds(transform: (B) -> T): Sequence<Pair<A, T>> =
    map { it.mapSecond(transform) }

@JvmName("iterablePairMapSeconds")
fun <A, B, T> Iterable<Pair<A, B>>.mapSeconds(transform: (B) -> T): List<Pair<A, T>> =
    map { it.mapSecond(transform) }

@JvmName("sequenceTripleMapSeconds")
fun <A, B, C, T> Sequence<Triple<A, B, C>>.mapSeconds(transform: (B) -> T): Sequence<Triple<A, T, C>> =
    map { it.mapSecond(transform) }

@JvmName("iterableTripleMapSeconds")
fun <A, B, C, T> Iterable<Triple<A, B, C>>.mapSeconds(transform: (B) -> T): List<Triple<A, T, C>> =
    map { it.mapSecond(transform) }

/**
 * Maps only the third entry using [transform].
 */
fun <A, B, C, T> Triple<A, B, C>.mapThird(transform: (C) -> T): Triple<A, B, T> =
    Triple(first, second, transform(third))

/**
 * Maps the third entry in each tuple using [transform].
 */
@JvmName("sequenceTripleMapThirds")
fun <A, B, C, T> Sequence<Triple<A, B, C>>.mapThirds(transform: (C) -> T): Sequence<Triple<A, B, T>> =
    map { it.mapThird(transform) }

@JvmName("iterableTripleMapThirds")
fun <A, B, C, T> Iterable<Triple<A, B, C>>.mapThirds(transform: (C) -> T): List<Triple<A, B, T>> =
    map { it.mapThird(transform) }

/**
 * Retains only tuples of which the first entry matches the [condition].
 */
@JvmName("sequencePairFilterFirsts")
fun <A, B> Sequence<Pair<A, B>>.filterFirsts(condition: (A) -> Boolean): Sequence<Pair<A, B>> =
    filter { condition(it.first) }

@JvmName("iterablePairFilterFirsts")
fun <A, B> Iterable<Pair<A, B>>.filterFirsts(condition: (A) -> Boolean): List<Pair<A, B>> =
    filter { condition(it.first) }

@JvmName("sequenceTripleFilterFirsts")
fun <A, B, C> Sequence<Triple<A, B, C>>.filterFirsts(condition: (A) -> Boolean): Sequence<Triple<A, B, C>> =
    filter { condition(it.first) }

@JvmName("iterableTripleFilterFirsts")
fun <A, B, C> Iterable<Triple<A, B, C>>.filterFirsts(condition: (A) -> Boolean): List<Triple<A, B, C>> =
    filter { condition(it.first) }

/**
 * Retains only tuples of which the second entry matches the [condition].
 */
@JvmName("sequencePairFilterSeconds")
fun <A, B> Sequence<Pair<A, B>>.filterSeconds(condition: (B) -> Boolean): Sequence<Pair<A, B>> =
    filter { condition(it.second) }

@JvmName("iterablePairFilterSeconds")
fun <A, B> Iterable<Pair<A, B>>.filterSeconds(condition: (B) -> Boolean): List<Pair<A, B>> =
    filter { condition(it.second) }

@JvmName("sequenceTripleFilterSeconds")
fun <A, B, C> Sequence<Triple<A, B, C>>.filterSeconds(condition: (B) -> Boolean): Sequence<Triple<A, B, C>> =
    filter { condition(it.second) }

@JvmName("iterableTripleFilterSeconds")
fun <A, B, C> Iterable<Triple<A, B, C>>.filterSeconds(condition: (B) -> Boolean): List<Triple<A, B, C>> =
    filter { condition(it.second) }

/**
 * Retains only tuples of which the third entry matches the [condition].
 */
@JvmName("sequenceTripleFilterThirds")
fun <A, B, C> Sequence<Triple<A, B, C>>.filterThirds(condition: (C) -> Boolean): Sequence<Triple<A, B, C>> =
    filter { condition(it.third) }

@JvmName("iterableTripleFilterThirds")
fun <A, B, C> Iterable<Triple<A, B, C>>.filterThirds(condition: (C) -> Boolean): List<Triple<A, B, C>> =
    filter { condition(it.third) }

/**
 * Returns a pair containing the first two elements.
 */
fun <T> List<T>.asPair(): Pair<T, T> = Pair(this[0], this[1])

/**
 * Returns a pair containing the first three elements.
 */
fun <T> List<T>.asTriple(): Triple<T, T, T> = Triple(this[0], this[1], this[2])

/**
 * Zips [Pair.first] with [Pair.second].
 */
fun <A, B> Pair<Iterable<A>, Iterable<B>>.zipped(): List<Pair<A, B>> = first zip second


/**
 * Repeats [this] sequence [amount] times.
 */
fun <T> Sequence<T>.repeat(amount: Int): Sequence<T> = sequence { for (i in 1..amount) yieldAll(this@repeat) }

fun <T> Iterable<T>.repeat(amount: Int): List<T> = asSequence().repeat(amount).toList()

/**
 * Repeats [this] sequence infinitely many times.
 */
fun <T> Sequence<T>.cyclic(): Sequence<T> = sequence { while (true) yieldAll(this@cyclic) }

fun <T> Iterable<T>.cyclic(): Sequence<T> = asSequence().cyclic()

/**
 * Without modifying [this], first returns all elements except the first [amount], and then returns those first
 * [amount] elements.
 *
 * For example, left-shifting `012345` by 3 will return in the order `3450123`.
 */
fun <T> Iterable<T>.leftShifted(amount: Int): Sequence<T> =
    sequence {
        yieldAll(drop(amount))
        yieldAll(take(amount))
    }

/**
 * Like [leftShifted], but in the other direction.
 *
 * Requires turning this into a [Collection] first, because you cannot return the last elements first without knowing
 * how many elements there are.
 */
fun <T> Iterable<T>.rightShifted(amount: Int): Sequence<T> = toList().let { it.leftShifted(it.size - amount) }

/**
 * Returns `false` if an element occurs more than once, or `true` if all elements are unique. Assumes transitive
 * equality.
 *
 * This method does not terminate if the sequence is infinitely long.
 */
fun <T> Sequence<T>.allDistinct(): Boolean {
    val seen = mutableSetOf<T>()
    return all { seen.add(it) }
}

fun <T> Iterable<T>.allDistinct(): Boolean = asSequence().allDistinct()

fun <A, B> Pair<A, B>.allDistinct(): Boolean = first != second

fun <A, B, C> Triple<A, B, C>.allDistinct(): Boolean = first != second && first != third && second != third

/**
 * Returns `true` if all elements are equal, or `false` if not all elements are equal. Assumes transitive equality.
 *
 * This method does not terminate if the sequence is infinitely long.
 */
fun <T> Sequence<T>.noneDistinct(): Boolean {
    val first = take(1).first()
    return drop(1).all { it == first }
}

fun <T> Iterable<T>.noneDistinct(): Boolean = asSequence().noneDistinct()

fun <A, B> Pair<A, B>.noneDistinct(): Boolean = first == second

fun <A, B, C> Triple<A, B, C>.noneDistinct(): Boolean = first == second && first == third

/**
 * Returns `true` if and only if `this` contains exactly [target] elements that match the [predicate].
 *
 * If there are more than [target] elements that match the [predicate], then this method stops iterating over the
 * sequence. In those cases, this method is faster than doing `count(predicate) == target`.
 */
fun <T> Sequence<T>.hasCountExactly(target: Int, predicate: (T) -> Boolean): Boolean =
    target == fold(0) { counted, element ->
        if (!predicate(element)) counted
        else (counted + 1).also { if (it > target) return false }
    }


/**
 * Removes the element at [idx], and returns [this] list.
 */
fun <T> List<T>.without(idx: Int): MutableList<T> = toMutableList().also { it.removeAt(idx) }

/**
 * Removes the given [element], or does nothing if the [element] is not in `this`.
 */
fun <T> Set<T>.without(element: T): Set<T> = if (element in this) minusElement(element) else this

/**
 * Returns a copy of this list in which the [idx]th element is mapped according to [modify].
 */
fun <T> List<T>.mapAt(idx: Int, modify: (T) -> T): List<T> = toMutableList().also { it[idx] = modify(it[idx]) }

/**
 * Returns a copy of this list in which the last element is mapped according to [modify].
 */
fun <T> List<T>.mapLast(modify: (T) -> T): List<T> = mapAt(lastIndex, modify)

/**
 * Swaps the elements at [idx1] and [idx2], and returns [this] list.
 */
fun <T> MutableList<T>.swapAt(idx1: Int, idx2: Int): MutableList<T> {
    this[idx1] = this[idx2].also { this[idx2] = this[idx1] }
    return this
}

/**
 * Associates each element by its index in the list, with the index being the key.
 */
fun <T> List<T>.associateByIndex(): Map<Int, T> = withIndex().associateBy({ it.index }, { it.value })

/**
 * Associates each element with its index in the list, with the index being the value.
 */
fun <T> List<T>.associateWithIndex(): Map<T, Int> = withIndex().associateBy({ it.value }, { it.index })


/**
 * Merges [this] with [that] by selecting elements from both collections in an alternating fashion, starting with
 * [this].
 *
 * If the collections have different sizes, then the unmatched part of the longer collection is simply appended.
 */
fun <T> Collection<T>.unzip(that: Collection<T>): List<T> {
    val common = min(this.size, that.size)
    return this.take(common).zip(that.take(common)).flatMap { it.toList() } + this.drop(common) + that.drop(common)
}


/**
 * Like `iterator`, but without [ConcurrentModificationException]s when elements are appended after the iterator has
 * been created.
 */
fun <T> MutableList<T>.appendableIterator(): Iterator<T> =
    iterator {
        var i = 0
        while (i < size) {
            yield(get(i))
            i++
        }
    }

/**
 * Like `onEach`, but without [ConcurrentModificationException]s when [action] appends elements to the list.
 *
 * [action] now takes one extra argument in front of the classic argument, which is a reference to [this].
 */
fun <T> MutableList<T>.appendOnEach(action: (MutableList<T>, T) -> Unit): MutableList<T> =
    this.also { appendableIterator().forEach { action(this, it) } }

/**
 * Like `fold`, but without [ConcurrentModificationException]s when [operation] appends elements to the list.
 *
 * [operation] now takes one extra argument in front of the two classic arguments, which is a reference to [this].
 */
fun <T, R> MutableList<T>.appendingFold(initial: R, operation: (MutableList<T>, R, T) -> R): R {
    var acc = initial
    appendOnEach { self, it -> acc = operation(self, acc, it) }
    return acc
}

/**
 * Returns [this] map after mapping [key] to [value].
 */
fun <K, V> MutableMap<K, V>.with(key: K, value: V): MutableMap<K, V> = this.also { put(key, value) }


/**
 * Returns all pairs of all maps.
 */
fun <K, V> Sequence<Map<K, V>>.flatten(): Sequence<Pair<K, V>> = flatMap { it.toList() }

fun <K, V> Iterable<Map<K, V>>.flatten(): Iterable<Pair<K, V>> = flatMap { it.toList() }

/**
 * Folds the elements of this map.
 */
fun <K, V, R> Map<K, V>.fold(initial: R, operation: (acc: R, Pair<K, V>) -> R): R = toList().fold(initial, operation)


/**
 * A map that accesses an underlying [map] without requiring boring `null` checks.
 *
 * For example, instead of `map["key"]!!`, just write `map["key"]`.
 */
class NeverNullMap<K, V : Any>(private val map: Map<K, V>) : Map<K, V> by map {
    override fun get(key: K): V = map[key]!!
}

/**
 * Returns a [NeverNullMap] that wraps around `this`.
 */
fun <K, V : Any> Map<K, V>.neverNull(): NeverNullMap<K, V> = NeverNullMap(this)
