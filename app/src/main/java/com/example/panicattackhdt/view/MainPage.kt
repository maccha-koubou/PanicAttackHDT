package com.example.panicattackhdt.view

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.panicattackhdt.model.RecordData
import com.example.panicattackhdt.ui.theme.White
import com.example.panicattackhdt.navigation.AppNavDestination
import com.example.panicattackhdt.navigation.SubScreenDestination
import com.example.panicattackhdt.service.generateHR
import com.example.panicattackhdt.ui.theme.Typography
import com.example.panicattackhdt.ui.theme.navigationBarColors
import kotlinx.coroutines.delay
import androidx.compose.ui.text.TextStyle
import com.example.panicattackhdt.ui.theme.Blue
import com.example.panicattackhdt.ui.theme.Gray
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Slider
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.text.style.TextAlign
import com.example.panicattackhdt.service.getSeverityColor
import com.example.panicattackhdt.ui.theme.Black

val localNavController = compositionLocalOf<NavController> {
    error("No navigation found!")
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MainPage() {
    var hr by remember { mutableStateOf(60) }
    var t by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        while (true) {
            hr = generateHR(t)
            t += 1
            delay(1000L)
        }
    }



    var prevHr by remember { mutableStateOf(60) }
    var belowStartTime by remember { mutableStateOf<Int?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(hr) {
        if (prevHr > 100 && hr <= 100) {
            belowStartTime = t
        }

        belowStartTime?.let { startTime ->
            if (hr <= 100) {
                if (t - startTime >= 10) {
                    showDialog = true
                    belowStartTime = null
                }
            } else {
                belowStartTime = null
            }
        }
        prevHr = hr
    }

    
    if (showDialog) {
        var severity by remember { mutableStateOf(5) }

        AlertDialog(
            containerColor = White,
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { showDialog = false }
                ) {
                    Text("Confirm")
                }
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "How did you feel\nduring this PA",
                        style = Typography.titleLarge,
                        color = Black,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "${severity}",
                        style = TextStyle(
                            color = getSeverityColor(severity),
                            fontSize = 56.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "out of 10",
                        style = Typography.labelMedium,
                        color = Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Slider(
                        value = severity * 1f,
                        onValueChange = { severity = it.toInt() },
                        valueRange = 1f..10f,
                        steps = 8
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Mild",
                            style = Typography.labelMedium,
                            color = Gray
                        )
                        Text(
                            text = "Severe",
                            style = Typography.labelMedium,
                            color = Gray
                        )
                    }
                }
            }
        )
    }


    val navController = rememberNavController()

    // List of main screens
    val screens: List<AppNavDestination> = listOf(
        AppNavDestination.HomeScreen,
        AppNavDestination.HistoryScreen,
        AppNavDestination.CoachingScreen,
        AppNavDestination.StressScreen,
        AppNavDestination.ProfileScreen
    )

    // Navigation bar
    // Use CompositionLocal to make sure that each component insides this area can access the navController
    CompositionLocalProvider(localNavController provides navController) {
        Scaffold(
            bottomBar = {
                NavigationBar(
                    containerColor = White
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    screens.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = screen.icon,
                                    contentDescription = null
                                    //modifier = Modifier.size(32.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = screen.title,
                                    style = Typography.labelMedium
                                )
                            },
                            colors = navigationBarColors,
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home_screen",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = AppNavDestination.HomeScreen.route) {
                    HomeScreen(navController, hr)
                }
                composable(route = AppNavDestination.HistoryScreen.route) {
                    HistoryScreen()
                }
                composable(route = AppNavDestination.CoachingScreen.route) {
                    //CoachingScreen()
                    BreathScreen(navController, hr)
                }
                composable(route = AppNavDestination.StressScreen.route) {
                    StressScreen()
                }
                composable(route = AppNavDestination.ProfileScreen.route) {
                    ProfileScreen()
                }
                composable(route = SubScreenDestination.HRScreen.route) {
                    HRScreen(navController)
                }
                composable(route = SubScreenDestination.BreathScreen.route) {
                    BreathScreen(navController, hr)
                }
            }
        }
    }
}