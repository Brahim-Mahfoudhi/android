package rise.tiao1.buut.presentation.notification.notificationList

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import rise.tiao1.buut.R
import rise.tiao1.buut.presentation.components.InfoContainer
import rise.tiao1.buut.presentation.home.HomeScreenState

@Composable
fun NotificationList(state: HomeScreenState) {

    when {
        state.notifications.isEmpty() -> {
            InfoContainer(stringResource(R.string.work_in_progress))
        }
    }
}