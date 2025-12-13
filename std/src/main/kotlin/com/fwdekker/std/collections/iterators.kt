package com.fwdekker.std.collections


/**
 * An [Iterator] that supports the peek operation.
 *
 * Taken from https://stackoverflow.com/a/77179888/
 */
interface PeekingIterator<T> : Iterator<T> {
    fun peek(): T
}

/**
 * Returns a [PeekingIterator] over this [Iterator].
 *
 * Taken from https://stackoverflow.com/a/77179888/
 */
fun <T> Iterator<T>.peeking() =
    object : PeekingIterator<T> {
        private var hasPeeked = false
        private var peeked: T? = null

        override fun hasNext(): Boolean =
            hasPeeked || this@peeking.hasNext()

        override fun next(): T =
            if (hasPeeked) {
                hasPeeked = false
                @Suppress("UNCHECKED_CAST")
                peeked as T
            } else {
                this@peeking.next()
            }

        /**
         * Returns the [next] element without advancing the iterator.
         */
        override fun peek(): T {
            if (!hasPeeked) {
                peeked = this@peeking.next()
                hasPeeked = true
            }

            @Suppress("UNCHECKED_CAST")
            return peeked as T
        }
    }

/**
 * Advances the [Iterator] as long as [condition] holds, returning all elements for which [condition] holds, such that
 * the iterator will at the end point at the first element for which [condition] does not hold.
 */
fun <T> PeekingIterator<T>.takeWhile(condition: (T) -> Boolean): Sequence<T> =
    sequence {
        while (hasNext() && condition(peek()))
            yield(next())
    }
