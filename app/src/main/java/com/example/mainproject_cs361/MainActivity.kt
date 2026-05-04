package com.example.mainproject_cs361

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.mainproject_cs361.compose.composables.BottomNavBar
import com.example.mainproject_cs361.compose.composables.TopAppBar
import com.example.mainproject_cs361.compose.nav.AppRoutes
import com.example.mainproject_cs361.theme.AppTheme
import com.example.mainproject_cs361.compose.features.home.HomeScreen
import com.example.mainproject_cs361.compose.features.schedule.ScheduleScreen
import com.example.mainproject_cs361.utils.MockClassRepository
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val repository = remember { MockClassRepository() }

            AppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavBar(navController) },
                    topBar = { TopAppBar() }
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = AppRoutes.home,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<AppRoutes.Home> {
                            HomeScreen(
                                repository = repository,
                                onNavigateToSchedule = { navController.navigate(AppRoutes.Schedule()) }
                            )
                        }

                        composable<AppRoutes.Schedule> {
                            ScheduleScreen(repository = repository)
                        }
                    }
                }
            }
        }
    }
}
