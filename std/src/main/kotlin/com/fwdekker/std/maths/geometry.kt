package com.fwdekker.std.maths

import com.fwdekker.std.maths.vector.LongVector


/**
 * A 2D line, consisting of an [origin] and an [orientation].
 */
data class Ray2D(val origin: LongVector, val orientation: LongVector) {
    /**
     * The slope of this line.
     */
    private val slope: Double = orientation[1].toDouble() / orientation[0]

    /**
     * The y-coordinate at which this line intersects with the y-axis.
     */
    private val yIntercept: Double = origin[1] - origin[0] * slope


    init {
        require(origin.size == 2) { "Origin must be a 2D vector." }
        require(orientation.size == 2) { "Orientation must be a 2D vector." }
    }


    /**
     * Returns `true` if and only if [that] intersects with `this`, and such that the x- and y-coordinates of the
     * intersection are both in the specified range [within].
     */
    fun intersects(that: Ray2D, within: LongRange?): Boolean {
        if (this.slope == that.slope)
            return this.yIntercept == that.yIntercept

        val x = (that.yIntercept - this.yIntercept) / (this.slope - that.slope)
        val y = this.slope * x + this.yIntercept

        return (within == null || x in within && y in within) &&
            x.compareTo(this.origin[0]) == this.orientation[0].compareTo(0) &&
            x.compareTo(that.origin[0]) == that.orientation[0].compareTo(0)
    }
}
