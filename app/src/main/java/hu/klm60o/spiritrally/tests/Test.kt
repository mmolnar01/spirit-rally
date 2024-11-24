package hu.klm60o.spiritrally.tests

import hu.klm60o.spiritrally.data.UserViewModel
import hu.klm60o.spiritrally.useful.round

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

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

class ValidateEmailTest {
    lateinit var mockedUserViewModel: UserViewModel

    @Before
    fun init() {
        mockedUserViewModel = Mockito.mock(UserViewModel::class.java)
        Mockito.`when`(mockedUserViewModel.validateEmail("test@test.com")).thenReturn(true)
        Mockito.`when`(mockedUserViewModel.validateEmail("test@test")).thenReturn(false)
    }

    @Test
    fun email_isCorrect() {
        assertTrue(mockedUserViewModel.validateEmail("test@test.com"))
    }

    @Test
    fun email_isIncorrect() {
        assertFalse(mockedUserViewModel.validateEmail("test@test"))
    }
}