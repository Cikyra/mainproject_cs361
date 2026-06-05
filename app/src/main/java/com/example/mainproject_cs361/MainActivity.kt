package com.example.mainproject_cs361

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.launch
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.compose.runtime.collectAsState
import com.example.mainproject_cs361.compose.composables.BottomNavBar
import com.example.mainproject_cs361.compose.composables.TopAppBar
import com.example.mainproject_cs361.compose.nav.AppRoutes
import com.example.mainproject_cs361.theme.AppTheme
import com.example.mainproject_cs361.compose.features.home.HomeScreen
import com.example.mainproject_cs361.compose.features.schedule.ScheduleScreen
import com.example.mainproject_cs361.compose.features.splash.SplashScreen
import com.example.mainproject_cs361.compose.features.login.LoginScreen
import com.example.mainproject_cs361.data.repo.features.auth.AuthRepository
import com.example.mainproject_cs361.utils.MockClassRepository
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.content.edit


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val sharedPref = getPreferences(MODE_PRIVATE)
        val isFirstLaunch = sharedPref.getBoolean("isFirstLaunch", true)

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val repository = remember { MockClassRepository() }
            val authRepository = remember { AuthRepository() }
            val user by authRepository.authState.collectAsState()

            val isSplashScreen = currentDestination?.hasRoute<AppRoutes.SplashScreen>() == true
            val isLoginScreen = currentDestination?.hasRoute<AppRoutes.Login>() == true
            val showBars = !isSplashScreen && !isLoginScreen

            val startDestination: Any = if (isFirstLaunch) {
                AppRoutes.SplashScreen
            } else {
                if (user != null) AppRoutes.home else AppRoutes.Login
            }

            val context = LocalContext.current
            val permissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (!isGranted) {
                    Toast.makeText(context, "Notification permission is required for reminders.", Toast.LENGTH_LONG).show()
                }
            }

            LaunchedEffect(Unit) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            }

            LaunchedEffect(user) {
                if (user != null) {
                    val success = authRepository.registerFcmToken()
                    if (!success) {
                        Toast.makeText(context, "Failed to sync notification token", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            AppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { if (showBars) BottomNavBar(navController) },
                    topBar = {
                        if (showBars) {
                            TopAppBar(
                                onLogout = {
                                    authRepository.signOut()
                                    navController.navigate(AppRoutes.Login) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                        modifier = Modifier.padding(if (showBars) innerPadding else PaddingValues(0.dp))
                    ) {
                        composable<AppRoutes.SplashScreen> {
                            SplashScreen(onTimeout = {
                                sharedPref.edit {
                                    putBoolean("isFirstLaunch", false)
                                }
                                if (user != null) {
                                    navController.navigate(AppRoutes.home) {
                                        popUpTo(AppRoutes.SplashScreen) { inclusive = true }
                                    }
                                } else {
                                    navController.navigate(AppRoutes.Login) {
                                        popUpTo(AppRoutes.SplashScreen) { inclusive = true }
                                    }
                                }
                            })
                        }

                        composable<AppRoutes.Login> {
                            LoginScreen(onLoginSuccess = {
                                navController.navigate(AppRoutes.home) {
                                    popUpTo(AppRoutes.Login) { inclusive = true }
                                }
                            })
                        }

                        composable<AppRoutes.Home> {
                            HomeScreen(
                                repository = repository,
                                onNavigateToSchedule = { navController.navigate(AppRoutes.Schedule()) }
                            )
                        }

                        composable<AppRoutes.Schedule> {
                            ScheduleScreen(
                                repository = repository,
                                authRepository = authRepository
                            )
                        }
                    }
                }
            }
        }
    }
}
