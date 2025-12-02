package com.fwdekker.std

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe


/**
 * Tests that a given [Challenge] has the expected outputs for given inputs.
 *
 * @param challenge creates the [Challenge] to test, given the (optional) sample number
 * @param cases tests to run, expressed as [case]s mapped to expected outputs
 * @constructor
 */
abstract class ChallengeTest(
    challenge: (Int?) -> Challenge,
    cases: Collection<Pair<Pair<Int, Int?>, Any>>
) : FunSpec({
    withData(nameFn = { "sample $it" }, cases) { (input, expected) ->
        val (part, sample) = input
        challenge(sample).runPart(part) shouldBe expected
    }
}) {
    /**
     * Holds constants.
     */
    companion object {
        /**
         * A test case for [ChallengeTest].
         */
        fun case(part: Int = 1, sample: Int? = null): Pair<Int, Int?> = Pair(part, sample)
    }
}
