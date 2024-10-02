package rise.tiao1.buut.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import rise.tiao1.buut.ui.theme.AppTheme
import rise.tiao1.buut.navigation.BottomNavItem

import rise.tiao1.buut.navigation.NavigationKeys
import rise.tiao1.buut.R
/**
 * Composable function for a scaffold with a common menu structure.
 *
 * @param navController NavController used for navigation.
 * @param body Composable representing the main content of the screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScaffold(navController: NavController, body: @Composable () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val isAuthRoute = navController.currentDestination?.route == NavigationKeys.Route.AUTH
    if (isAuthRoute) {
        body()
    } else {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                Header(scrollBehavior)
            },
            bottomBar = {
                Footer(navController)
            },
        ) { paddingValues ->

            Box(Modifier.padding(top = paddingValues.calculateTopPadding(), bottom = paddingValues.calculateBottomPadding())) {
                body()
            }

        }
    }
}

/**
 * Composable function for the bottom navigation bar with specific menu items.
 *
 * @param navController NavController used for navigation.
 */
@Composable
fun Footer(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Profile
    )
    NavigationBar {
        items.forEach { item ->
            AddItem(
                screen = item,
                route = { navController.navigate(item.route) }
            )
        }
    }
}

/**
 * Composable function for the top app bar header.
 *
 * @param scrollBehavior TopAppBarScrollBehavior for handling scroll behavior.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(scrollBehavior: TopAppBarScrollBehavior) {

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = stringResource(R.string.initial_title),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        scrollBehavior = scrollBehavior,
    )
}

/**
 * Preview function for the Footer composable.
 */
@Preview
@Composable
fun FooterPreview() {
    AppTheme {
        Footer(navController = rememberNavController())
    }
}

/**
 * Composable function for adding an item to the bottom navigation bar.
 *
 * @param screen BottomNavItem representing the menu item.
 * @param route Lambda function for handling navigation to the specified route.
 */
@Composable
fun RowScope.AddItem(screen: BottomNavItem, route: () -> Unit) {
    NavigationBarItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                painterResource(id = screen.icon),
                contentDescription = screen.title
            )
        },
        selected = true,
        alwaysShowLabel = true,
        onClick = { route() },
        colors = NavigationBarItemDefaults.colors()
    )
}
