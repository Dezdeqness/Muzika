package com.dezdeqness.muzika.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dezdeqness.core.ui.theme.AppTheme
import com.dezdeqness.muzika.presentation.AquaBottomTabModel

@Composable
fun AppNavBar(
    modifier: Modifier = Modifier,
    height: Dp,
    navController: NavHostController,
) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        containerColor = AppTheme.colors.background,
        tonalElevation = 4.dp,
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState().value
        val currentDestination = navBackStackEntry?.destination

        AquaBottomTabModel.entries.forEach { item ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.hasRoute(item.route::class)
            } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                    )
                },
            )
        }
    }
}
