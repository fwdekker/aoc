package com.fwdekker.std.maths

import com.fwdekker.containExactlyInAnyOrderElementsOf
import com.fwdekker.std.collections.repeat
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.be
import io.kotest.matchers.should


/**
 * Unit tests for `combinatorics.kt`.
 */
object CombinatoricsTest : DescribeSpec({
    describe("factorial") {
        withData(
            nameFn = { "${it.first}!" },
            -1 to 1,
            0 to 1,
            1 to 1,
            2 to 2,
            3 to 6,
            4 to 24,
            5 to 120,
            6 to 720,
            7 to 5040,
        ) { (input, expected) -> input.factorial().toInt() should be(expected) }

        withData(
            nameFn = { "${it.first}! / (${it.first} - ${it.second})!" },
            Pair(-1, 1) to 1,
            Pair(-1, 0) to 1,
            Pair(0, -1) to 1,
            Pair(0, 0) to 1,
            Pair(0, 1) to 1,
            Pair(1, -1) to 1,
            Pair(1, 0) to 1,
            Pair(1, 1) to 1,
            Pair(1, 2) to 1,
            Pair(2, -1) to 1,
            Pair(2, 0) to 1,
            Pair(2, 1) to 2,
            Pair(2, 2) to 2,
            Pair(2, 3) to 2,
            Pair(3, -1) to 1,
            Pair(3, 0) to 1,
            Pair(3, 1) to 3,
            Pair(3, 2) to 6,
            Pair(3, 3) to 6,
            Pair(3, 4) to 6,
            Pair(4, -1) to 1,
            Pair(4, 0) to 1,
            Pair(4, 1) to 4,
            Pair(4, 2) to 12,
            Pair(4, 3) to 24,
            Pair(4, 4) to 24,
            Pair(4, 5) to 24,
        ) { (inputs, expected) -> inputs.first.factorial(inputs.second).toInt() should be(expected) }
    }

    describe("choose") {
        it("errors if n is negative") {
            shouldThrow<IllegalArgumentException> { (-1).toBigInteger().choose(3.toBigInteger()) }
        }

        it("errors if k is negative") {
            shouldThrow<IllegalArgumentException> { 3.toBigInteger().choose((-1).toBigInteger()) }
        }

        withData(
            nameFn = { "${it.first}C${it.second}" },
            Pair(0, 0) to 1,
            Pair(0, 1) to 0,
            Pair(1, 0) to 1,
            Pair(1, 1) to 1,
            Pair(1, 2) to 0,
            Pair(2, 0) to 1,
            Pair(2, 1) to 2,
            Pair(2, 2) to 1,
            Pair(3, 0) to 1,
            Pair(3, 1) to 3,
            Pair(3, 2) to 3,
            Pair(3, 3) to 1,
            Pair(3, 4) to 0,
            Pair(4, 0) to 1,
            Pair(4, 1) to 4,
            Pair(4, 2) to 6,
            Pair(4, 3) to 4,
            Pair(4, 4) to 1,
            Pair(4, 5) to 0,
            Pair(5, 0) to 1,
            Pair(5, 1) to 5,
            Pair(5, 2) to 10,
            Pair(5, 3) to 10,
            Pair(5, 4) to 5,
            Pair(5, 5) to 1,
            Pair(5, 6) to 0,
            Pair(6, 0) to 1,
            Pair(6, 1) to 6,
            Pair(6, 2) to 15,
            Pair(6, 3) to 20,
            Pair(6, 4) to 15,
            Pair(6, 5) to 6,
            Pair(6, 6) to 1,
            Pair(6, 7) to 0,
        ) { (inputs, expected) -> inputs.first.choose(inputs.second) should be(expected.toBigInteger()) }
    }


    describe("cartesian") {
        withData(
            nameFn = { "${it.first.first} X ${it.first.second}" },
            (emptyList<Int>() to emptyList<Int>()) to emptyList(),
            (emptyList<Int>() to listOf(1)) to emptyList(),
            (listOf(1) to emptyList<Int>()) to emptyList(),
            (listOf(1) to listOf(2)) to listOf(1 to 2),
            (listOf(1) to listOf(2, 3)) to listOf(1 to 2, 1 to 3),
            (listOf(1, 2) to listOf(3)) to listOf(1 to 3, 2 to 3),
            (listOf(1, 2) to listOf(3, 4)) to listOf(1 to 3, 1 to 4, 2 to 3, 2 to 4),
            (listOf(1, 2, 3) to listOf(4, 5, 6)) to
                listOf(1 to 4, 1 to 5, 1 to 6, 2 to 4, 2 to 5, 2 to 6, 3 to 4, 3 to 5, 3 to 6),
        ) { (inputs, expected) ->
            inputs.first.cartesian(inputs.second).toList() should containExactlyInAnyOrderElementsOf(expected)
        }
    }

    describe("powerSet") {
        val all = listOf(
            emptyList(),
            listOf(1),
            listOf(2),
            listOf(3),
            listOf(1, 2),
            listOf(1, 3),
            listOf(2, 3),
            listOf(1, 2, 3),
        )

        withData(
            nameFn = { "all combinations of 1..${it.first}" },
            0 to listOf(emptyList()),
            1 to listOf(emptyList(), listOf(1)),
            2 to listOf(emptyList(), listOf(1), listOf(2), listOf(1, 2)),
            3 to all,
        ) { (input, expected) ->
            (1..input).toList().powerSet().toList() should containExactlyInAnyOrderElementsOf(expected)
        }

        withData(
            nameFn = { "all combinations of 1..3 with minimum size ${it.first}" },
            0 to all,
            1 to listOf(listOf(1), listOf(2), listOf(3), listOf(1, 2), listOf(1, 3), listOf(2, 3), listOf(1, 2, 3)),
            2 to listOf(listOf(1, 2), listOf(1, 3), listOf(2, 3), listOf(1, 2, 3)),
            3 to listOf(listOf(1, 2, 3)),
        ) { (minSize, expected) ->
            listOf(1, 2, 3).powerSet(minSize = minSize).toList() should containExactlyInAnyOrderElementsOf(expected)
        }

        withData(
            nameFn = { "all combinations of 1..3 with maximum size ${it.first}" },
            0 to listOf(emptyList()),
            1 to listOf(emptyList(), listOf(1), listOf(2), listOf(3)),
            2 to listOf(emptyList(), listOf(1), listOf(2), listOf(3), listOf(1, 2), listOf(1, 3), listOf(2, 3)),
            3 to all,
        ) { (maxSize, expected) ->
            listOf(1, 2, 3).powerSet(maxSize = maxSize).toList() should containExactlyInAnyOrderElementsOf(expected)
        }

        withData(
            nameFn = { "all combinations of 1..3 with exact size ${it.first}" },
            0 to listOf(emptyList()),
            1 to listOf(listOf(1), listOf(2), listOf(3)),
            2 to listOf(listOf(1, 2), listOf(1, 3), listOf(2, 3)),
            3 to listOf(listOf(1, 2, 3)),
        ) { (size, expected) ->
            listOf(1, 2, 3).powerSet(minSize = size, maxSize = size).toList() should
                containExactlyInAnyOrderElementsOf(expected)
        }

        withData(
            nameFn = { "all combinations of 1..3 with size in range ${it.first.first}..${it.first.second}" },
            (0 to 0) to listOf(emptyList()),
            (1 to 2) to listOf(listOf(1), listOf(2), listOf(3), listOf(1, 2), listOf(1, 3), listOf(2, 3)),
            (1 to 3) to listOf(
                listOf(1),
                listOf(2),
                listOf(3),
                listOf(1, 2),
                listOf(1, 3),
                listOf(2, 3),
                listOf(1, 2, 3)
            ),
            (2 to 3) to listOf(listOf(1, 2), listOf(1, 3), listOf(2, 3), listOf(1, 2, 3)),
        ) { (range, expected) ->
            listOf(1, 2, 3).powerSet(minSize = range.first, maxSize = range.second).toList() should
                containExactlyInAnyOrderElementsOf(expected)
        }
    }

    describe("permutations") {
        withData(
            nameFn = { "all permutations of ${it.first}" },
            emptyList<Int>() to emptyList(),
            1..1 to listOf(listOf(1)),
            1..2 to listOf(listOf(1, 2), listOf(2, 1)),
            1..3 to listOf(
                listOf(1, 2, 3), listOf(1, 3, 2), listOf(2, 1, 3), listOf(2, 3, 1), listOf(3, 1, 2), listOf(3, 2, 1),
            ),
            1..4 to listOf(
                listOf(1, 2, 3, 4), listOf(1, 2, 4, 3), listOf(1, 3, 2, 4),
                listOf(1, 3, 4, 2), listOf(1, 4, 2, 3), listOf(1, 4, 3, 2),
                listOf(2, 1, 3, 4), listOf(2, 1, 4, 3), listOf(2, 3, 1, 4),
                listOf(2, 3, 4, 1), listOf(2, 4, 1, 3), listOf(2, 4, 3, 1),
                listOf(3, 1, 2, 4), listOf(3, 1, 4, 2), listOf(3, 2, 1, 4),
                listOf(3, 2, 4, 1), listOf(3, 4, 1, 2), listOf(3, 4, 2, 1),
                listOf(4, 1, 2, 3), listOf(4, 1, 3, 2), listOf(4, 2, 1, 3),
                listOf(4, 2, 3, 1), listOf(4, 3, 1, 2), listOf(4, 3, 2, 1),
            )
        ) { (elements, expected) ->
            elements.permutations().toList() should containExactlyInAnyOrderElementsOf(expected)
        }

        it("considers duplicated inputs as separate elements") {
            val expected = listOf(listOf(0, 0, 1), listOf(0, 1, 0), listOf(1, 0, 0)).repeat(2)

            listOf(0, 0, 1).permutations().toList() should containExactlyInAnyOrderElementsOf(expected)
            listOf(0, 1, 0).permutations().toList() should containExactlyInAnyOrderElementsOf(expected)
        }
    }
})
