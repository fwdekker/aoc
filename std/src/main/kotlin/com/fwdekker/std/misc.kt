package com.fwdekker.std

import kotlin.time.Duration


/**
 * Shorthand for [Duration.inWholeMilliseconds].
 */
val Duration.ms get() = inWholeMilliseconds


/**
 * Memorizes/caches outputs of [body].
 *
 * Recursive calls in the [body] are memorized only if they are done using [DeepRecursiveScope.callRecursive].
 */
fun <I, O> memoised(body: suspend DeepRecursiveScope<I, O>.(I) -> O): DeepRecursiveFunction<I, O> {
    val memory = mutableMapOf<I, O>()
    return DeepRecursiveFunction { memory.getOrPut(it) { body(it) } }
}
