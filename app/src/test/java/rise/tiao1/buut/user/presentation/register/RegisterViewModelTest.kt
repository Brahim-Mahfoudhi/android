package rise.tiao1.buut.user.presentation.register

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.text.SimpleDateFormat
import java.util.Calendar


class RegisterViewModelTest {

    lateinit var toTest: RegisterViewModel

    @Before
    fun setUp() {
        toTest = RegisterViewModel()
    }

    @Test
    fun `password is valid when it meets all criteria`() {
        val password = "Password1!"
        assertTrue(toTest.isPasswordValid(password))
    }

    @Test
    fun `password is invalid when it has less than 8 characters`() {
        val password = "Pass1!"
        assertFalse(toTest.isPasswordValid(password))
    }

    @Test
    fun `password is invalid when it does not contain a capital letter`() {
        val password = "password1!"
        assertFalse(toTest.isPasswordValid(password))
    }

    @Test
    fun `password is invalid when it does not contain a number`() {
        val password = "Password!"
        assertFalse(toTest.isPasswordValid(password))
    }

    @Test
    fun `password is invalid when it does not contain a special character`() {
        val password = "Password1"
        assertFalse(toTest.isPasswordValid(password))
    }

    @Test
    fun `age is valid when user is exactly 18`() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, -18)
        val date = SimpleDateFormat("dd/MM/yyyy").format(calendar.time)

        assertTrue(toTest.isUserAtLeast18(date))
    }

    @Test
    fun `age is invalid when user is under 18`() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, -17) // User is 17 years old
        val date = SimpleDateFormat("dd/MM/yyyy").format(calendar.time)

        assertFalse(toTest.isUserAtLeast18(date))
    }

    @Test
    fun `age is valid when user is over 18`() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, -20) // User is 20 years old
        val date = SimpleDateFormat("dd/MM/yyyy").format(calendar.time)

        assertTrue(toTest.isUserAtLeast18(date))
    }
    
    @Test
    fun `valid phone number 10 digits`() {
        val phone = "1234567890"
        assertTrue(toTest.isTelephoneValid(phone))
    }

    @Test
    fun `valid phone number 9 digits`() {
        val phone = "123456789"
        assertTrue(toTest.isTelephoneValid(phone))
    }
    @Test
    fun `invalid phone number with letters`() {
        val phone = "12345abcde"
        assertFalse(toTest.isTelephoneValid(phone))
    }
}