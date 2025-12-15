package com.fwdekker.std


/**
 * Returns the current thread's class loader.
 */
fun getClassLoader(): ClassLoader = Thread.currentThread().contextClassLoader

/**
 * Returns the (whitespace-trimmed) contents of resource [path].
 */
fun read(path: String): String =
    getClassLoader().getResource(path)
        ?.readText()?.trim()
        ?: error("Could not find resource $path.")
