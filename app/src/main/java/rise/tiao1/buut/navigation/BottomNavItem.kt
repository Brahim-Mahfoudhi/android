package rise.tiao1.buut.navigation

import rise.tiao1.buut.R


/**
 * Sealed class representing items in the bottom navigation bar.
 *
 * @param title The title of the bottom navigation item.
 * @param icon The icon resource ID of the bottom navigation item.
 * @param route The route associated with the bottom navigation item.
 */
sealed class BottomNavItem(val title: String, val icon: Int, val route: String = "") {
    /**
     * Represents the "Home" bottom navigation item.
     */
    data object Home: BottomNavItem("Home", R.drawable.baseline_home_24, NavigationKeys.Route.HOME  )

    /**
     * Represents the "Profile" bottom navigation item.
     */
    data object Profile: BottomNavItem("Profile",R.drawable.baseline_account_box_24, NavigationKeys.Route.PROFILE)

}
