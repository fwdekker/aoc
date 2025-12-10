package com.fwdekker.std.maths


/**
 * A 3-dimensional line, consisting of an [origin] and an [orientation].
 */
data class Ray2D(val origin: LVec2, val orientation: LVec2) {
    /**
     * The slope of this line.
     */
    private val slope: Double = orientation.second.toDouble() / orientation.first

    /**
     * The y-coordinate at which this line intersects with the y-axis.
     */
    private val yIntercept: Double = origin.second - origin.first * slope


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
            x.compareTo(this.origin.first) == this.orientation.first.compareTo(0) &&
            x.compareTo(that.origin.first) == that.orientation.first.compareTo(0)
    }
}
