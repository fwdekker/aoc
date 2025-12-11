package com.fwdekker.std.maths

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe


/**
 * Unit tests for `numbers.kt`.
 */
object NumbersTest : DescribeSpec({
    describe("intLength") {
        it("should return length 1 for 0") {
            0.length() shouldBe 1
        }

        withData(
            nameFn = { "$it should have its length correctly estimated" },
            1..<Int.MAX_VALUE.toString().length
        ) { length -> "1".repeat(length).toInt().length() shouldBe length }

        withData(
            nameFn = { "$it should have its length correctly estimated" },
            1..<Int.MAX_VALUE.toString().length
        ) { length -> "-${"1".repeat(length)}".toInt().length() shouldBe (length + 1) }
    }

    describe("longLength") {
        it("should return length 1 for 0") {
            0L.length() shouldBe 1
        }

        withData(
            nameFn = { "should correctly calculate length $it for a positive number" },
            1..<Long.MAX_VALUE.toString().length
        ) { length -> "1".repeat(length).toLong().length() shouldBe length }

        withData(
            nameFn = { "should correctly calculate length $it for a negative number" },
            1..<Long.MAX_VALUE.toString().length
        ) { length -> "-${"1".repeat(length)}".toLong().length() shouldBe (length + 1) }
    }
})
