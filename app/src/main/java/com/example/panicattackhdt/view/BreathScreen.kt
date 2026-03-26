package com.example.panicattackhdt.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.panicattackhdt.ui.theme.BackgroundGray
import com.example.panicattackhdt.ui.theme.Black
import com.example.panicattackhdt.ui.theme.Gray
import com.example.panicattackhdt.ui.theme.LightGray
import com.example.panicattackhdt.ui.theme.Typography
import com.example.panicattackhdt.ui.theme.White
import com.example.panicattackhdt.ui.theme.cardColors
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.CartesianMeasuringContext
import com.patrykandpatrick.vico.core.cartesian.axis.Axis
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreathScreen(navController: NavController, hr: Int) {
    var second by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            second = when (second) {
                15 -> 0
                else -> (second + 1)
            }
        }
    }
    val phase = when(second) {
        in 0..3 -> 0
        in 4..7 -> 1
        in 8..11 -> 2
        else -> 3
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarColors(Color(0xFF1a3ab2), Color(0xFF1a3ab2), White, White, White),
                title = {
                    Text("Breathing")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {
            innerPadding ->
        Column(
            modifier = Modifier
                .background(Color(0xFF1a3ab2))
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 120.dp)
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    val targetScale = when (phase) {
                        1 -> 1.5f
                        2 -> 1.5f
                        else -> 1f
                    }
                    val scale by animateFloatAsState(
                        targetValue = targetScale,
                        animationSpec = tween(durationMillis = 4000)
                    )
                    Box(
                        modifier = Modifier
                            .size(180.dp)
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                            }
                            .background(Color(0xFF7490ff), shape = CircleShape)
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(0.dp, 60.dp, 0.dp, 0.dp)
                    ) {
                        Text(
                            text = "Heart Rate / BPM",
                            style = Typography.titleSmall,
                            color = White
                        )
                        Text(
                            text = "$hr",
                            style = TextStyle(
                                color = White,
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            color = White
                        )
                        Spacer(
                            Modifier.height(300.dp)
                        )
                        Text(
                            text = when(phase) {
                                0 -> "Rest"
                                1 -> "Breathe in"
                                2 -> "Hold"
                                else -> "Breathe out"
                            },
                            style = TextStyle(
                                color = White,
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            color = White
                        )
                        Text(
                            text = "${4 - second % 4}",
                            style = TextStyle(
                                color = White,
                                fontSize = 56.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            color = White
                        )
                    }
                }
            }
        }
    }
}