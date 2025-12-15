package com.fwdekker.std.maths.vector

import java.math.BigDecimal


fun BigDecimalMatrix.rref() {
    var pivotRow = 0
    var pivotCol = 0
    while (pivotRow in rowIndices && pivotCol in colIndices) {
        val candidate = rowIndices.drop(pivotRow).maxBy { get(it, pivotCol).abs() }
        if (get(candidate, pivotCol).compareTo(BigDecimal.ZERO) == 0) {
            pivotCol++
            continue
        }

        swapRows(pivotRow, candidate)
        getRow(pivotRow) /= get(pivotRow, pivotCol)
        rowIndices.minus(pivotRow).forEach { getRow(it) += getRow(pivotRow) * -get(it, pivotCol) }

        pivotRow++
        pivotCol++
    }
}
