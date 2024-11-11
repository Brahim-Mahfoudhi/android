package rise.tiao1.buut.presentation.booking.createBooking

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import rise.tiao1.buut.R
import rise.tiao1.buut.utils.UiLayout

class CreateBookingScreenTest {
    @get:Rule
    val rule = createComposeRule()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun createBookingScreen_loadingState_showsLoadingIndicator() {
        rule.setContent {
                CreateBookingScreen(
                    state = CreateBookingScreenState(datesAreLoading = true),
                    onReadyForUpdate = {},
                    uiLayout = UiLayout.PORTRAIT_SMALL
                )
            }
        rule.onNodeWithTag(context.getString(R.string.loading_indicator)).assertIsDisplayed()
        rule.onNodeWithTag(context.getString(R.string.calendar)).assertIsNotDisplayed()
        rule.onNodeWithTag(context.getString(R.string.info_container)).assertIsNotDisplayed()

        }

    @Test
    fun createBookingScreen_errorState_displaysErrorMessage() {
        val errorMessage = "Failed to fetch dates"

        rule.setContent {
                CreateBookingScreen(
                    state = CreateBookingScreenState(getFreeDatesError = errorMessage),
                    onReadyForUpdate = {},
                    uiLayout = UiLayout.PORTRAIT_SMALL
                )
            }
        rule.onNodeWithText(errorMessage).assertIsDisplayed()

        }

    @Test
    fun createBookingScreen_portraitLayout_showsDatePickerAndTimeSlots() {
        rule.setContent {

                CreateBookingScreen(
                    state = CreateBookingScreenState(datesAreLoading = false),
                    onReadyForUpdate = {},
                    uiLayout = UiLayout.PORTRAIT_SMALL
                )

        }

        rule.onNodeWithTag(context.getString(R.string.calendar)).assertIsDisplayed()
        rule.onNodeWithTag(context.getString(R.string.info_container)).assertIsDisplayed()
    }

    @Test
    fun createBookingScreen_landscapeLayout_displaysComponentsCorrectly() {
        rule.setContent {
                CreateBookingScreen(
                    state = CreateBookingScreenState(datesAreLoading = false),
                    onReadyForUpdate = {},
                    uiLayout = UiLayout.LANDSCAPE_SMALL
                )
        }

        rule.onNodeWithTag(context.getString(R.string.calendar)).assertIsDisplayed()
        rule.onNodeWithTag(context.getString(R.string.info_container)).assertIsDisplayed()
    }

}