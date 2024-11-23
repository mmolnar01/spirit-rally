package hu.klm60o.spiritrally.tests

import hu.klm60o.spiritrally.useful.round

import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

class DoubleRoundTest {
    @Test
    fun doubleRound_isCorrect() {
        assertEquals(15.1568.round(2), 15.16, 0.01)
    }
}