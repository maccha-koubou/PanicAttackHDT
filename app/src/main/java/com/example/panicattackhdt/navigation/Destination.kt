package com.example.panicattackhdt.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppNavDestination(val title: String, val route: String, val icon: ImageVector) {
    object HomeScreen : AppNavDestination("Home", "home_screen", Icons.Outlined.Home)
    object HistoryScreen : AppNavDestination("History", "history_screen", Icons.Outlined.DateRange)
    object CoachingScreen : AppNavDestination("Coaching", "coaching_screen", Icons.Outlined.PlayArrow)
    object StressScreen : AppNavDestination("Stress", "stress_screen", Icons.Outlined.Info)
    object ProfileScreen : AppNavDestination("Profile", "profile_screen", Icons.Outlined.AccountCircle)


}

sealed class SubScreenDestination(val route: String) {
    object HRScreen : SubScreenDestination("hr_screen")
    object BreathScreen : SubScreenDestination("breath_screen")
}