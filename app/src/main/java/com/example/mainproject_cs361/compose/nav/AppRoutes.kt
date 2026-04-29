package com.example.mainproject_cs361.compose.nav


import com.example.mainproject_cs361.R
import kotlinx.serialization.Serializable

interface TopLevelRoute {
    val name: Int
    val activeIcon: Int
    val inactiveIcon: Int
}

object AppRoutes {
    @Serializable
    object SplashScreen

    @Serializable
    data class Home(
        override val name: Int = R.string.home,
        override val activeIcon: Int = R.drawable.home_icon_active,
        override val inactiveIcon: Int = R.drawable.home_icon_inactive
    ): TopLevelRoute
    val home = Home()

    @Serializable
    data class Schedule(
        override val name: Int = R.string.schedule,
        override val activeIcon: Int = R.drawable.schedule_icon_active,
        override val inactiveIcon: Int = R.drawable.schedule_icon_inactive
    ): TopLevelRoute
    private val schedule = Schedule()

    val topLevelRoutes = listOf(home, schedule)
}