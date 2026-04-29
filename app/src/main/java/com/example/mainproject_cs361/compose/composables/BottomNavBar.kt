package com.example.mainproject_cs361.compose.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mainproject_cs361.compose.nav.AppRoutes

@Composable
fun BottomNavBar(navController: NavController) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.onPrimary) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        AppRoutes.topLevelRoutes.forEach { route ->
            val name = stringResource(route.name)
            val routeIsActive = currentDestination?.hierarchy?.any { it.hasRoute(route::class) } == true
            val icon = if (routeIsActive) route.activeIcon else route.inactiveIcon

            NavigationBarItem(
                icon = {
                    Icon(painter = painterResource(id = icon), contentDescription = name)
                },
                label = { Text(name) },
                selected = routeIsActive,
                onClick = {
                    navController.navigate(route)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    }
}