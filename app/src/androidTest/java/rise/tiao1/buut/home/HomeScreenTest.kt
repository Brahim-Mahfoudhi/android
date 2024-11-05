package rise.tiao1.buut.home

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import rise.tiao1.buut.R
import rise.tiao1.buut.domain.booking.Booking
import rise.tiao1.buut.domain.user.User
import rise.tiao1.buut.presentation.home.HomeScreen
import rise.tiao1.buut.presentation.home.HomeScreenState
import rise.tiao1.buut.utils.UiLayout.PORTRAIT_SMALL
import java.time.LocalDateTime

class HomeScreenTest {
    @get:Rule
    val rule = createComposeRule()
    val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun homeScreen_loadingState_isDisplayedCorrectly(){
        rule.setContent {
            HomeScreen(
                state = getState(isLoading = true),
                logout = {},
                toReservationPage = {},
                uiLayout = PORTRAIT_SMALL
            )
        }
        rule.onNodeWithTag("LoadingIndicator").assertIsDisplayed()
        rule.onNodeWithTag("BookingItem").assertIsNotDisplayed()
        rule.onNodeWithText(context.getString(R.string.user_has_no_bookings))
    }

    @Test
    fun homeScreen_errorState_isDisplayedCorrectly() {
        val errorMessage = "Error fetching data"

        rule.setContent {
            HomeScreen(
                state = getState(apiError = errorMessage),
                logout = {},
                toReservationPage = {},
                uiLayout = PORTRAIT_SMALL
            )
        }

        rule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun homeScreen_noBookingsMessage_isDisplayedCorrectly() {
        rule.setContent {
            HomeScreen(
                state = HomeScreenState(bookings = emptyList(), isLoading = false),
                logout = {},
                toReservationPage = {},
                uiLayout = PORTRAIT_SMALL
            )
        }

        rule.onNodeWithText(context.getString(R.string.user_has_no_bookings)).assertIsDisplayed()
    }

    @Test
    fun homeScreen_bookingsList_isDisplayed() {
        val bookings = listOf(
            Booking(date = LocalDateTime.now().plusDays(1)),
            Booking(date = LocalDateTime.now().plusDays(2)),
            Booking(date = LocalDateTime.now().plusDays(3))
        )

        rule.setContent {
            HomeScreen(
                state = getState(bookings = bookings),
                logout = {},
                toReservationPage = {},
                uiLayout = PORTRAIT_SMALL
            )
        }

        val displayedBookings = rule.onAllNodesWithTag("UpcomingBooking").fetchSemanticsNodes()
        assert(displayedBookings.size == bookings.size)
    }

    @Test
    fun homeScreen_onlyFirstUpcomingBooking_isExpanded() {
        val bookings = listOf(
            Booking(date = LocalDateTime.now().minusDays(2)),
            Booking(date = LocalDateTime.now().plusDays(1), boat = "expandedBoat", battery = "expandedBattery"),
            Booking(date = LocalDateTime.now().plusDays(2), boat="collapsedBoat", battery = "collapsedBattery")
        )

        rule.setContent {
            HomeScreen(
                state = getState(bookings = bookings),
                logout = {},
                toReservationPage = {},
                uiLayout = PORTRAIT_SMALL
            )
        }

        rule.onNodeWithText("Boat: expandedBoat").assertIsDisplayed()
        rule.onNodeWithText("Battery: expandedBattery").assertIsDisplayed()
        rule.onNodeWithText("Boat: collapsedBoat").assertIsNotDisplayed()
        rule.onNodeWithText("Battery: collapsedBattery").assertIsNotDisplayed()
    }

    @Test
    fun homeScreen_pastBookings_areFaded() {
        val pastBooking = Booking(date = LocalDateTime.now().minusDays(1))

        rule.setContent {
            HomeScreen(
                state = getState(bookings = listOf(pastBooking)),
                logout = {},
                toReservationPage = {},
                uiLayout = PORTRAIT_SMALL
            )
        }

        rule.onNodeWithTag("PastBooking").assertIsDisplayed()
        rule.onNodeWithTag("UpcomingBooking").assertIsNotDisplayed()
    }

    @Test
    fun homeScreen_expandBooking_onIconClick() {
        val booking = Booking(date = LocalDateTime.now().minusDays(2), boat = "testBoat")

        rule.setContent {
            HomeScreen(
                state = getState(bookings = listOf(booking)),
                logout = {},
                toReservationPage = {},
                uiLayout = PORTRAIT_SMALL
            )
        }

        rule.onNodeWithTag("ExpandButton").performClick()
        rule.onNodeWithText("${context.getString(R.string.boat)}: ${booking.boat}").assertIsDisplayed()
    }

    @Test
    fun homeScreen_collapseBooking_onIconClick() {
        val booking = Booking(date = LocalDateTime.now().minusDays(1), boat = "testBoat")

        rule.setContent {
            HomeScreen(
                state = getState(bookings = listOf(booking)),
                logout = {},
                toReservationPage = {},
                uiLayout = PORTRAIT_SMALL
            )
        }

        rule.onNodeWithContentDescription(context.getString(R.string.expand_button_content_description)).performClick()
        rule.onNodeWithContentDescription(context.getString(R.string.expand_button_content_description)).performClick()
        rule.onNodeWithText("${context.getString(R.string.boat)}: ${booking.boat}").assertDoesNotExist()
    }

    @Test
    fun homeScreen_missingBoatMessage_isDisplayed() {
        val booking = Booking(date = LocalDateTime.now().plusDays(1), boat = null)

        rule.setContent {
            HomeScreen(
                state = getState(bookings = listOf(booking)),
                logout = {},
                toReservationPage = {},
                uiLayout = PORTRAIT_SMALL
            )
        }

        rule.onNodeWithText(context.getString(R.string.no_boat_assigned)).assertIsDisplayed()
    }

    @Test
    fun homeScreen_missingBatteryMessage_isDisplayed() {
        val booking = Booking(date = LocalDateTime.now().plusDays(1), battery = null)

        rule.setContent {
            HomeScreen(
                state = HomeScreenState(bookings = listOf(booking), isLoading = false),
                logout = {},
                toReservationPage = {},
                uiLayout = PORTRAIT_SMALL
            )
        }

        rule.onNodeWithText(context.getString(R.string.no_battery_assigned)).assertIsDisplayed()
    }


    private fun getState(user: User? = null, bookings : List<Booking> = emptyList(), isLoading : Boolean = false, apiError: String = "" ) : HomeScreenState {
        return HomeScreenState(
            user = user,
            bookings = bookings,
            isLoading = isLoading,
            apiError = apiError)
    }
}