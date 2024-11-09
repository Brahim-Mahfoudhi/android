package rise.tiao1.buut.presentation.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.device.DeviceInteraction.Companion.setScreenOrientation
import androidx.test.espresso.device.EspressoDevice.Companion.onDevice
import androidx.test.espresso.device.action.ScreenOrientation
import androidx.test.espresso.device.rules.ScreenOrientationRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rise.tiao1.buut.R
import rise.tiao1.buut.domain.booking.Booking
import rise.tiao1.buut.domain.user.User
import rise.tiao1.buut.utils.UiLayout.LANDSCAPE_EXPANDED
import rise.tiao1.buut.utils.UiLayout.LANDSCAPE_MEDIUM
import rise.tiao1.buut.utils.UiLayout.LANDSCAPE_SMALL
import rise.tiao1.buut.utils.UiLayout.PORTRAIT_EXPANDED
import rise.tiao1.buut.utils.UiLayout.PORTRAIT_MEDIUM
import rise.tiao1.buut.utils.UiLayout.PORTRAIT_SMALL
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {
    @get:Rule
    val rule = createComposeRule()
    @get:Rule
    val screenOrientationRule: ScreenOrientationRule = ScreenOrientationRule(ScreenOrientation.PORTRAIT)
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setStartOrientation() {
        onDevice().setScreenOrientation(ScreenOrientation.PORTRAIT)
    }

    @Test
    fun homeScreen_loadingState_isDisplayedCorrectly(){
        rule.setContent {
            HomeScreen(
                state = getState(isLoading = true),
                logout = {},
                navigateTo = {},
                uiLayout = PORTRAIT_SMALL
            )
        }

        rule.onNodeWithTag(context.getString(R.string.booking_list_title)).performClick()
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
                navigateTo = {},
                uiLayout = PORTRAIT_SMALL
            )
        }

        rule.onNodeWithTag(context.getString(R.string.booking_list_title)).performClick()
        rule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun homeScreen_noBookingsMessage_isDisplayedCorrectly() {
        rule.setContent {
            HomeScreen(
                state = HomeScreenState(bookings = emptyList(), isLoading = false),
                logout = {},
                navigateTo = {},
                uiLayout = PORTRAIT_SMALL
            )
        }

        rule.onNodeWithTag(context.getString(R.string.booking_list_title)).performClick()
        rule.onNodeWithText(context.getString(R.string.user_has_no_bookings)).assertIsDisplayed()
    }

    @Test
    fun homeScreen_bookingsList_isDisplayed() {
        val bookings = listOf(
            Booking(id= "1", date = LocalDateTime.now().plusDays(1)),
            Booking(id= "2", date = LocalDateTime.now().plusDays(2)),
            Booking(id= "3", date = LocalDateTime.now().plusDays(3))
        )

        rule.setContent {
            HomeScreen(
                state = getState(bookings = bookings),
                logout = {},
                navigateTo = {},
                uiLayout = PORTRAIT_SMALL
            )
        }

        rule.onNodeWithTag(context.getString(R.string.booking_list_title)).performClick()
        rule.onNodeWithTag(context.getString(R.string.booking_list_title)).performClick()
        val displayedBookings = rule.onAllNodesWithTag("UpcomingBooking").fetchSemanticsNodes()
        assert(displayedBookings.size == bookings.size)
    }

    @Test
    fun homeScreen_onlyFirstUpcomingBooking_isExpanded() {
        val bookings = listOf(
            Booking(id= "1", date = LocalDateTime.now().minusDays(2)),
            Booking(id= "2", date = LocalDateTime.now().plusDays(1), boat = "expandedBoat", battery = "expandedBattery"),
            Booking(id= "3", date = LocalDateTime.now().plusDays(2), boat="collapsedBoat", battery = "collapsedBattery")
        )

        rule.setContent {
            HomeScreen(
                state = getState(bookings = bookings),
                logout = {},
                navigateTo = {},
                uiLayout = PORTRAIT_SMALL
            )
        }

        rule.onNodeWithTag(context.getString(R.string.booking_list_title)).performClick()
        rule.onNodeWithText("Boat: expandedBoat").assertIsDisplayed()
        rule.onNodeWithText("Battery: expandedBattery").assertIsDisplayed()
        rule.onNodeWithText("Boat: collapsedBoat").assertIsNotDisplayed()
        rule.onNodeWithText("Battery: collapsedBattery").assertIsNotDisplayed()
    }

    @Test
    fun homeScreen_pastBookings_areFaded() {
        val pastBooking = Booking(id= "1", date = LocalDateTime.now().minusDays(1))

        rule.setContent {
            HomeScreen(
                state = getState(bookings = listOf(pastBooking)),
                logout = {},
                navigateTo = {},
                uiLayout = PORTRAIT_SMALL
            )
        }

        rule.onNodeWithTag(context.getString(R.string.booking_list_title)).performClick()

        rule.onNodeWithTag("PastBooking").assertIsDisplayed()
        rule.onNodeWithTag("UpcomingBooking").assertIsNotDisplayed()
    }

    @Test
    fun homeScreen_expandBooking_onIconClick() {
        val booking = Booking(id= "1", date = LocalDateTime.now().minusDays(2), boat = "testBoat")

        rule.setContent {
            HomeScreen(
                state = getState(bookings = listOf(booking)),
                logout = {},
                navigateTo = {},
                uiLayout = PORTRAIT_SMALL
            )
        }

        rule.onNodeWithTag(context.getString(R.string.booking_list_title)).performClick()
        rule.onNodeWithTag("ExpandButton").performClick()
        rule.onNodeWithText("${context.getString(R.string.boat)}: ${booking.boat}").assertIsDisplayed()
    }

    @Test
    fun homeScreen_collapseBooking_onIconClick() {
        val booking = Booking(id= "1", date = LocalDateTime.now().minusDays(1), boat = "testBoat")

        rule.setContent {
            HomeScreen(
                state = getState(bookings = listOf(booking)),
                logout = {},
                navigateTo = {},
                uiLayout = PORTRAIT_SMALL
            )
        }

        rule.onNodeWithTag(context.getString(R.string.booking_list_title)).performClick()
        rule.onNodeWithContentDescription(context.getString(R.string.expand_button_content_description)).performClick()
        rule.onNodeWithContentDescription(context.getString(R.string.expand_button_content_description)).performClick()
        rule.onNodeWithText("${context.getString(R.string.boat)}: ${booking.boat}").assertDoesNotExist()
    }

    @Test
    fun homeScreen_missingBoatMessage_isDisplayed() {
        val booking = Booking(id= "1", date = LocalDateTime.now().plusDays(1), boat = null)

        rule.setContent {
            HomeScreen(
                state = getState(bookings = listOf(booking)),
                logout = {},
                navigateTo = {},
                uiLayout = PORTRAIT_SMALL
            )
        }

        rule.onNodeWithTag(context.getString(R.string.booking_list_title)).performClick()
        rule.onNodeWithText(context.getString(R.string.no_boat_assigned)).assertIsDisplayed()
    }

    @Test
    fun homeScreen_missingBatteryMessage_isDisplayed() {
        val booking = Booking(id= "1", date = LocalDateTime.now().plusDays(1), battery = null)

        rule.setContent {
            HomeScreen(
                state = HomeScreenState(bookings = listOf(booking), isLoading = false),
                logout = {},
                navigateTo = {},
                uiLayout = PORTRAIT_SMALL
            )
        }

        rule.onNodeWithTag(context.getString(R.string.booking_list_title)).performClick()
        rule.onNodeWithTag(context.getString(R.string.booking_list_title)).performClick()
        rule.onNodeWithText(context.getString(R.string.no_battery_assigned)).assertIsDisplayed()
    }

    @Test
    fun homeScreen_compactDevicePortraitLayout_verifyUsingBottomNavigation(){
        onDevice().setScreenOrientation(ScreenOrientation.PORTRAIT)

        rule.setContent {
            HomeScreen(
                state = getState(),
                logout = {},
                navigateTo = {},
                uiLayout = PORTRAIT_SMALL
            )
        }

        rule.onNodeWithTag(context.getString(R.string.navigation_bar)).assertExists()
    }

    @Test
    fun homeScreen_compactDeviceLandscapeLayout_verifyUsingRailNavigation(){
        onDevice().setScreenOrientation(ScreenOrientation.LANDSCAPE)

        rule.setContent {
            HomeScreen(
                state = getState(),
                logout = {},
                navigateTo = {},
                uiLayout = LANDSCAPE_SMALL
            )
        }

        rule.onNodeWithTag(context.getString(R.string.navigation_rail)).assertExists()
    }

    @Test
    fun homeScreen_mediumDevicePortraitLayout_verifyUsingNavigationBar(){
        onDevice().setScreenOrientation(ScreenOrientation.PORTRAIT)

        rule.setContent {
            HomeScreen(
                state = getState(),
                logout = {},
                navigateTo = {},
                uiLayout = PORTRAIT_MEDIUM
            )
        }

        rule.onNodeWithTag(context.getString(R.string.navigation_bar)).assertExists()
    }

    @Test
    fun homeScreen_mediumDeviceLandscapeLayout_verifyUsingRailNavigation(){
        onDevice().setScreenOrientation(ScreenOrientation.LANDSCAPE)

        rule.setContent {
            HomeScreen(
                state = getState(),
                logout = {},
                navigateTo = {},
                uiLayout = LANDSCAPE_MEDIUM
            )
        }

        rule.onNodeWithTag(context.getString(R.string.navigation_rail)).assertExists()
    }

    @Test
    fun homeScreen_expandedDevicePortraitLayout_verifyUsingDrawerNavigation(){
        onDevice().setScreenOrientation(ScreenOrientation.PORTRAIT)

        rule.setContent {
            HomeScreen(
                state = getState(),
                logout = {},
                navigateTo = {},
                uiLayout = PORTRAIT_EXPANDED
            )
        }

        rule.onNodeWithTag(context.getString(R.string.navigation_drawer)).assertExists()
    }

    @Test
    fun homeScreen_expandedDeviceLandscapeLayout_verifyUsingDrawerNavigation(){
        onDevice().setScreenOrientation(ScreenOrientation.LANDSCAPE)

        rule.setContent {
            HomeScreen(
                state = getState(),
                logout = {},
                navigateTo = {},
                uiLayout = LANDSCAPE_EXPANDED
            )
        }

        rule.onNodeWithTag(context.getString(R.string.navigation_drawer)).assertExists()
    }


    private fun getState(user: User? = null, bookings : List<Booking> = emptyList(), isLoading : Boolean = false, apiError: String = "" ) : HomeScreenState {
        return HomeScreenState(
            user = user,
            bookings = bookings,
            isLoading = isLoading,
            apiError = apiError)
    }
}