package com.fwdekker.std

import com.fwdekker.std.grid.Cardinal
import com.fwdekker.std.grid.Direction
import com.fwdekker.std.grid.Ordinal
import kotlin.time.Duration
import kotlin.time.TimeSource
import kotlin.time.measureTimedValue


/**
 * Convenience class for invoking the code for any particular challenge with a file resource.
 *
 * @property partCount The number of parts that this challenge consists of.
 */
abstract class Challenge(private val partCount: Int) {
    /**
     * A reliable time source.
     */
    private val time = TimeSource.Monotonic

    /**
     * The moment in time at which the superclass' fields were first instantiated, marking a point in time before the
     * subclass' fields are instantiated.
     */
    private val mark = TimeSource.Monotonic.markNow()

    /**
     * The path to the resource from which the input is read, which is made available as [input].
     */
    protected abstract val resource: String

    /**
     * The input to the challenge.
     *
     * Lazily evaluated, in case there is no input.
     */
    val input: String by lazy { read(resource) }


    /**
     * Runs the part with given 1-indexed [number].
     */
    abstract fun runPart(number: Int): Any?

    /**
     * Runs each part and associates it with its duration and outputs. The 0th part is the instantiation of this object,
     * of which the output value is always the string `"Complete"`.
     */
    fun timeParts(): Sequence<PartResult> =
        sequence {
            yield(PartResult("Init", "Complete", time.markNow() - mark))
            (1..partCount).forEach { partNumber ->
                val partName = if (partCount == 1) "Solution" else "Part $partNumber"
                yield(measureTimedValue { runPart(partNumber) }.let { PartResult(partName, it.value, it.duration) })
            }
        }

    /**
     * Runs each part, and prints its output and the elapsed time.
     */
    fun run() =
        timeParts().forEach { (name, value, duration) ->
            println("$name: $value (${duration.ms} ms).")
        }


    /**
     * Holds constants.
     */
    companion object {
        /**
         * Ignore this field.
         *
         * TODO: Remove workaround for KT-59723
         */
        @Suppress("unused")
        private val ignoreMe = Triple(Cardinal, Ordinal, Direction)
    }
}

/**
 * The result of running a part of a [Challenge].
 */
data class PartResult(val name: String, val result: Any?, val duration: Duration)
