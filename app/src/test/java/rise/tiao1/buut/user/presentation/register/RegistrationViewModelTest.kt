package rise.tiao1.buut.user.presentation.register

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test
import rise.tiao1.buut.data.UserRepository
import rise.tiao1.buut.domain.user.useCases.RegisterUserUseCase
import rise.tiao1.buut.domain.user.validation.ValidateBox
import rise.tiao1.buut.domain.user.validation.ValidateDateOfBirth


import rise.tiao1.buut.domain.user.validation.ValidateEmail
import rise.tiao1.buut.domain.user.validation.ValidateFirstName
import rise.tiao1.buut.domain.user.validation.ValidateHouseNumber
import rise.tiao1.buut.domain.user.validation.ValidateLastName
import rise.tiao1.buut.domain.user.validation.ValidatePassword
import rise.tiao1.buut.domain.user.validation.ValidatePhone
import rise.tiao1.buut.domain.user.validation.ValidatePrivacy
import rise.tiao1.buut.domain.user.validation.ValidateRepeatedPassword
import rise.tiao1.buut.domain.user.validation.ValidateStreet
import rise.tiao1.buut.domain.user.validation.ValidateTerms
import rise.tiao1.buut.presentation.register.RegistrationScreenState
import rise.tiao1.buut.presentation.register.RegistrationViewModel



@ExperimentalCoroutinesApi
class RegistrationViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun initialState_isProduced() = scope.runTest {
        val viewModel = getViewModel()
        val initialState = viewModel.state.value
        assert(initialState == RegistrationScreenState())
    }


    private fun getViewModel(): RegistrationViewModel {
        val validateFirstName = ValidateFirstName()
        val validateLastName = ValidateLastName()
        val validateEmail = ValidateEmail()
        val validatePassword = ValidatePassword()
        val validateRepeatedPassword = ValidateRepeatedPassword()
        val validateStreet = ValidateStreet()
        val validateHouseNumber = ValidateHouseNumber()
        val validateBox = ValidateBox()
        val validateDateOfBirth = ValidateDateOfBirth()
        val validatePhone = ValidatePhone()
        val validateTerms = ValidateTerms()
        val validatePrivacy = ValidatePrivacy()
        val registerUserUseCase = RegisterUserUseCase(UserRepository(FakeUserDao(), FakeUserApiService()))

        return RegistrationViewModel(
            validateFirstName = validateFirstName,
            validateLastName = validateLastName,
            validateEmail = validateEmail,
            validatePassword = validatePassword,
            validateRepeatedPassword = validateRepeatedPassword,
            validateStreet = validateStreet,
            validateHouseNumber = validateHouseNumber,
            validateBox = validateBox,
            validateDateOfBirth = validateDateOfBirth,
            validatePhone = validatePhone,
            validateTerms = validateTerms,
            validatePrivacy = validatePrivacy,
            registerUserUseCase = registerUserUseCase
        )
    }
}