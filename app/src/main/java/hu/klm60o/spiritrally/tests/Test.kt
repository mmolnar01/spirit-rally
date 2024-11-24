package hu.klm60o.spiritrally.tests

import android.content.Context
import android.util.Patterns
import com.google.firebase.auth.FirebaseUser
import hu.klm60o.spiritrally.data.RacePoint
import hu.klm60o.spiritrally.data.UserViewModel
import hu.klm60o.spiritrally.useful.round

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyString
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

        Mockito.`when`(mockedUserViewModel.validateEmail(anyString())).thenReturn(false)
        Mockito.`when`(mockedUserViewModel.validateEmail(ArgumentMatchers.matches("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"))).thenReturn(true)

        Mockito.`when`(mockedUserViewModel.validatePasswordRepeat("jelszo", "jelszo")).thenReturn(true)
    }

    @Test
    fun email_isCorrect() {
        assertTrue(mockedUserViewModel.validateEmail("test@test.com"))
    }

    @Test
    fun email_isIncorrect() {
        assertFalse(mockedUserViewModel.validateEmail("test@test"))
    }

    @Test
    fun passwordRepeat_isCorrect() {
        assertTrue(mockedUserViewModel.validatePasswordRepeat("jelszo", "jelszo"))
    }
}

class GetRaceDataTest {
    lateinit var mockedUserViewModel: UserViewModel
    lateinit var mockedFirebaseUser: FirebaseUser
    lateinit var mockedContext: Context
    lateinit var racePoints: MutableList<RacePoint>

    @Before
    fun init() {
        mockedUserViewModel = Mockito.mock(UserViewModel::class.java)
        mockedFirebaseUser = Mockito.mock(FirebaseUser::class.java)
        mockedContext = Mockito.mock(Context::class.java)


        Mockito.`when`(mockedUserViewModel.getUserDataFromFirestore(mockedFirebaseUser, mockedContext))

        racePoints.add(RacePoint())
        racePoints.add(RacePoint())

    }

    @Test
    fun raceData_Get() {

    }
}