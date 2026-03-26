package com.example.panicattackhdt.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.panicattackhdt.model.RecordData
import com.example.panicattackhdt.model.Symptom
import com.example.panicattackhdt.navigation.SubScreenDestination
import com.example.panicattackhdt.service.getSeverityColor
import com.example.panicattackhdt.storage.DataStorage
import com.example.panicattackhdt.ui.theme.BackgroundGray
import com.example.panicattackhdt.ui.theme.Black
import com.example.panicattackhdt.ui.theme.Blue
import com.example.panicattackhdt.ui.theme.Gray
import com.example.panicattackhdt.ui.theme.LightGray
import com.example.panicattackhdt.ui.theme.Purple
import com.example.panicattackhdt.ui.theme.White
import com.example.panicattackhdt.ui.theme.largeMainButtonColors
import com.example.panicattackhdt.ui.theme.Typography
import com.example.panicattackhdt.ui.theme.cardColors
import com.example.panicattackhdt.ui.theme.emergencyButtonColors
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi", "UnrememberedMutableState")
@Composable
fun HomeScreen(navController: NavController, hr: Int) {
    val records = remember { DataStorage.getAll() }
    val dashboardColor = when (hr) {
        in 0..100 -> Color(0xFF00d368)
        in 101..150 -> Color(0xFFffbf00)
        else -> Color(0xFFE40000)
    }

    Column(Modifier.fillMaxSize()) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .background(BackgroundGray)
                .verticalScroll(scrollState)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {},
                    colors = emergencyButtonColors,
                    contentPadding = PaddingValues(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Call,
                        contentDescription = "Call for Help"
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Call for Help",
                        style = Typography.titleMedium,
                        color = White
                    )
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(360.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = cardColors,
                    border = BorderStroke(1.dp, LightGray)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(24.dp, 24.dp, 24.dp, 24.dp)
                            .fillMaxSize()
                    ) {
                        Text(
                            text = "Live Heart Rate",
                            style = Typography.titleLarge,
                            color = Black
                        )
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(144.dp)
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                drawArc(
                                    color = LightGray,
                                    startAngle = 0f,
                                    sweepAngle = 360f,
                                    useCenter = false,
                                    style = Stroke(width = 40f)
                                )
                                drawArc(
                                    color = dashboardColor,
                                    startAngle = -90f,
                                    sweepAngle = 360f * (hr / 180f),
                                    useCenter = false,
                                    style = Stroke(
                                        width = 40f,
                                        cap = StrokeCap.Round
                                    )
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    text = "$hr",
                                    style = TextStyle(
                                        color = Black,
                                        fontSize = 56.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = Black
                                )
                                Text(
                                    text = "BPM",
                                    style = Typography.titleSmall,
                                    color = Black
                                )
                            }
                        }
                        Button(
                            onClick = { when (hr) {
                                in 0..100 -> {}
                                else -> {
                                    navController.navigate(SubScreenDestination.BreathScreen.route)
                                }
                            }},
                            colors = ButtonColors(dashboardColor, White, Gray, White),
                            contentPadding = PaddingValues(16.dp),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = when (hr) {
                                    in 0..100 -> "You're stable"
                                    in 101..150 -> "Heart rate rising"
                                    else -> "Possible panic attack detected"
                                },
                                style = Typography.titleMedium,
                                color = White
                            )
                        }
                    }
                }


                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = cardColors,
                        border = BorderStroke(1.dp, LightGray)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                8.dp,
                                alignment = Alignment.CenterVertically
                            ),
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Text(
                                text = "123",
                                style = Typography.titleLarge,
                                color = Color(0xFF155dfc)
                            )
                            Text(
                                text = "Total Attacks",
                                style = Typography.titleSmall,
                                color = Gray
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = cardColors,
                        border = BorderStroke(1.dp, LightGray)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                8.dp,
                                alignment = Alignment.CenterVertically
                            ),
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Text(
                                text = "123",
                                style = Typography.titleLarge,
                                color = Color(0xFF9810fa)
                            )
                            Text(
                                text = "Last 30 Days",
                                style = Typography.titleSmall,
                                color = Gray
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = cardColors,
                        border = BorderStroke(1.dp, LightGray)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                8.dp,
                                alignment = Alignment.CenterVertically
                            ),
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Text(
                                text = "123",
                                style = Typography.titleLarge,
                                color = Color(0xFFf54900)
                            )
                            Text(
                                text = "Avg Severity",
                                style = Typography.titleSmall,
                                color = Gray
                            )
                        }
                    }
                }


                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonColors(White, Black, Gray, Gray),
                        border = BorderStroke(1.dp, LightGray),
                        onClick = {
                            navController.navigate(SubScreenDestination.HRScreen.route)
                        }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                8.dp,
                                alignment = Alignment.CenterVertically
                            ),
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.PlayArrow,
                                contentDescription = "Call for Help",
                                tint = Color(0xFF155dfc),
                                modifier = Modifier.size(32.dp)
                            )
                            Text(
                                text = "HR Trends",
                                style = Typography.titleSmall,
                                color = Gray
                            )
                        }
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonColors(White, Black, Gray, Gray),
                        border = BorderStroke(1.dp, LightGray),
                        onClick = {}
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                8.dp,
                                alignment = Alignment.CenterVertically
                            ),
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.List,
                                contentDescription = "Call for Help",
                                tint = Color(0xFF00a63e),
                                modifier = Modifier.size(32.dp)
                            )
                            Text(
                                text = "Health Reports",
                                style = Typography.titleSmall,
                                color = Gray
                            )
                        }
                    }
                }



                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = cardColors,
                    border = BorderStroke(1.dp, LightGray)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            16.dp,
                            alignment = Alignment.Top
                        ),
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxSize()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Recent Panic Attacks",
                                style = Typography.titleLarge,
                                color = Black
                            )
                            TextButton(
                                onClick = {},
                                contentPadding = PaddingValues(0.dp),
                                shape = RoundedCornerShape(0.dp),
                                modifier = Modifier.height(24.dp)
                            ) {
                                Text(
                                    text = "View All",
                                    style = Typography.titleMedium,
                                    color = Blue
                                )
                            }
                        }
                        records
                            .takeLast(3)
                            .reversed()
                            .forEach { record ->
                                PAListItem(record)
                        }
                    }
                }

            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PAListItem(record: RecordData) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonColors(BackgroundGray, Black, Gray, Gray),
        contentPadding = PaddingValues(0.dp),
        onClick = {}
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "${record.startTime.format(DateTimeFormatter.ofPattern("MMM dd, h:mm a"))}",
                    style = Typography.titleMedium,
                    color = Black
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = "●",
                        style = Typography.titleMedium,
                        color = getSeverityColor(record.severity)
                    )
                    Text(
                        text = "Severity ${record.severity}",
                        style = Typography.titleMedium,
                        color = Gray
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Info",
                    tint = Gray,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = " ${record.trigger}",
                    style = Typography.labelMedium,
                    color = Gray
                )
            }
        }
    }
}