package rise.tiao1.buut.user.presentation.register

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.time.LocalDate



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
    fun `date is invalid when day is 0`() {
        toTest.selectedDay = "0"
        toTest.selectedMonth = LocalDate.now().month.value.toString()
        toTest.selectedYear = LocalDate.now().minusYears(19L).year.toString();

        assertNotNull(toTest.validateDateOfBirth())
    }

    @Test
    fun `date is invalid when day is under 0`() {
        toTest.selectedDay = "-1"
        toTest.selectedMonth = LocalDate.now().month.value.toString()
        toTest.selectedYear = LocalDate.now().minusYears(19L).year.toString();

        assertNotNull(toTest.validateDateOfBirth())
    }

    @Test
    fun `date is invalid when day is over 31`() {
        toTest.selectedDay = "32"
        toTest.selectedMonth = LocalDate.now().month.value.toString()
        toTest.selectedYear = LocalDate.now().minusYears(19L).year.toString();

        assertNotNull(toTest.validateDateOfBirth())
    }

    @Test
    fun `date is invalid when month does not exist`() {
        toTest.selectedDay = LocalDate.now().dayOfMonth.toString()
        toTest.selectedMonth = "A fake month"
        toTest.selectedYear = LocalDate.now().minusYears(19L).year.toString();

        assertNotNull(toTest.validateDateOfBirth())
    }

    @Test
    fun `date is invalid when year is under current year -100`() {
        toTest.selectedDay = LocalDate.now().dayOfMonth.toString()
        toTest.selectedMonth = LocalDate.now().month.value.toString()
        toTest.selectedYear = LocalDate.now().minusYears(100L).year.toString();

        assertNotNull(toTest.validateDateOfBirth())
    }

    @Test
    fun `date is invalid when year is higher than current year`() {
        toTest.selectedDay = LocalDate.now().dayOfMonth.toString()
        toTest.selectedMonth = LocalDate.now().month.value.toString()
        toTest.selectedYear = LocalDate.now().plusYears(1L).year.toString();

        assertNotNull(toTest.validateDateOfBirth())
    }

    @Test
    fun `date is invalid when date is impossible`() {
        toTest.selectedDay = "30"
        toTest.selectedMonth = "February"
        toTest.selectedYear = LocalDate.now().minusYears(19L).year.toString();

        assertNotNull(toTest.validateDateOfBirth())
    }

    @Test
    fun `age is valid when user is exactly 18`() {
        toTest.selectedDay = LocalDate.now().dayOfMonth.toString()
        toTest.selectedMonth = LocalDate.now().month.value.toString()
        toTest.selectedYear = LocalDate.now().minusYears(18L).year.toString();

        assertNull(toTest.validateDateOfBirth())
    }

    @Test
    fun `age is invalid when user is under 18`() {
        toTest.selectedDay = LocalDate.now().dayOfMonth.toString()
        toTest.selectedMonth = LocalDate.now().month.value.toString()
        toTest.selectedYear = LocalDate.now().minusYears(16L).year.toString();

        assertNotNull(toTest.validateDateOfBirth())
    }

    @Test
    fun `age is valid when user is over 18`() {
        toTest.selectedDay = LocalDate.now().dayOfMonth.toString()
        toTest.selectedMonth = LocalDate.now().month.value.toString()
        toTest.selectedYear = LocalDate.now().minusYears(19L).year.toString();

        assertNull(toTest.validateDateOfBirth())
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