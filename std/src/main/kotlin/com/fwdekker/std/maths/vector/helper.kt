package com.fwdekker.std.maths.vector


fun IntVector.toLongVector(): LongVector = LongVector(LongArray(size) { get(it).toLong() })
fun IntVector.toDoubleVector(): DoubleVector = DoubleVector(DoubleArray(size) { get(it).toDouble() })
fun IntVector.toBigDecimalVector(): BigDecimalVector = BigDecimalVector(Array(size) { get(it).toBigDecimal() })

fun LongVector.toIntVector(): IntVector = IntVector(IntArray(size) { get(it).toInt() })
fun LongVector.toDoubleVector(): DoubleVector = DoubleVector(DoubleArray(size) { get(it).toDouble() })
fun LongVector.toBigDecimalVector(): BigDecimalVector = BigDecimalVector(Array(size) { get(it).toBigDecimal() })

fun DoubleVector.toIntVector(): IntVector = IntVector(IntArray(size) { get(it).toInt() })
fun DoubleVector.toLongVector(): LongVector = LongVector(LongArray(size) { get(it).toLong() })
fun DoubleVector.toBigDecimalVector(): BigDecimalVector = BigDecimalVector(Array(size) { get(it).toBigDecimal() })

fun BigDecimalVector.toIntVector(): IntVector = IntVector(IntArray(size) { get(it).toInt() })
fun BigDecimalVector.toLongVector(): LongVector = LongVector(LongArray(size) { get(it).toLong() })
fun BigDecimalVector.toDoubleVector(): DoubleVector = DoubleVector(DoubleArray(size) { get(it).toDouble() })


fun IntMatrix.toLongMatrix(): LongMatrix =
    LongMatrix(getRows().mapTo(mutableListOf()) { it.toLongVector() })
fun IntMatrix.toDoubleMatrix(): DoubleMatrix =
    DoubleMatrix(getRows().mapTo(mutableListOf()) { it.toDoubleVector() })
fun IntMatrix.toBigDecimalMatrix(): BigDecimalMatrix =
    BigDecimalMatrix(getRows().mapTo(mutableListOf()) { it.toBigDecimalVector() })

fun LongMatrix.toIntMatrix(): IntMatrix =
    IntMatrix(getRows().mapTo(mutableListOf()) { it.toIntVector() })
fun LongMatrix.toDoubleMatrix(): DoubleMatrix =
    DoubleMatrix(getRows().mapTo(mutableListOf()) { it.toDoubleVector() })
fun LongMatrix.toBigDecimalMatrix(): BigDecimalMatrix =
    BigDecimalMatrix(getRows().mapTo(mutableListOf()) { it.toBigDecimalVector() })

fun DoubleMatrix.toIntMatrix(): IntMatrix =
    IntMatrix(getRows().mapTo(mutableListOf()) { it.toIntVector() })
fun DoubleMatrix.toLongMatrix(): LongMatrix =
    LongMatrix(getRows().mapTo(mutableListOf()) { it.toLongVector() })
fun DoubleMatrix.toBigDecimalMatrix(): BigDecimalMatrix =
    BigDecimalMatrix(getRows().mapTo(mutableListOf()) { it.toBigDecimalVector() })

fun BigDecimalMatrix.toIntMatrix(): IntMatrix =
    IntMatrix(getRows().mapTo(mutableListOf()) { it.toIntVector() })
fun BigDecimalMatrix.toLongMatrix(): LongMatrix =
    LongMatrix(getRows().mapTo(mutableListOf()) { it.toLongVector() })
fun BigDecimalMatrix.toDoubleMatrix(): DoubleMatrix =
    DoubleMatrix(getRows().mapTo(mutableListOf()) { it.toDoubleVector() })
