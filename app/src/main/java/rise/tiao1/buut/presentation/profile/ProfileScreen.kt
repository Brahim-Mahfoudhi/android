package rise.tiao1.buut.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import rise.tiao1.buut.domain.user.Address
import rise.tiao1.buut.domain.user.User
import rise.tiao1.buut.presentation.components.LoadingIndicator
import rise.tiao1.buut.utils.StreetType
import rise.tiao1.buut.utils.UiLayout

@Composable
fun ProfileScreen(
    state: ProfileScreenState,
    logout: () -> Unit,
    navigateTo: (String) -> Unit,
    uiLayout: UiLayout
) {
    if (state.isLoading) {
        LoadingIndicator()
    } else {
        Column {
            Text("Welcome, ${state.user?.firstName} ${state.user?.lastName}")
            Button(onClick = logout) {
                Text("Logout")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        state = ProfileScreenState(user = getUser(), isLoading = false, apiError = ""),
        logout = {},
        navigateTo = {},
        uiLayout = UiLayout.PORTRAIT_SMALL)

}

fun getUser() : User{
    return User(
        id = "TestId",
        firstName = "TestFirstName",
        lastName = "TestLastName",
        email = "Test@Test.be",
        password = "TestPassword",
        phone = "TestPhoneNumber",
        dateOfBirth = "TestDateOfBirth",
        address = Address(StreetType.AFRIKALAAN, "TestHouseNumber", "TestBox")
    )
}