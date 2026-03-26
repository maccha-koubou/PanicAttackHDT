package com.example.panicattackhdt.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.panicattackhdt.storage.DataStorage
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
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HistoryScreen() {
    val records = remember { DataStorage.getAll().takeLast(7) }
    val peakList = records.map { it.peak }
    val dateList = records.map { it.startTime.format(DateTimeFormatter.ofPattern("MMM dd")) }

    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(peakList) {
        modelProducer.runTransaction {
            columnSeries {
                series(peakList)
            }
        }
    }

    Column(Modifier.fillMaxSize()) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGray)
                .verticalScroll(scrollState)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(16.dp)
            ) {
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
                                text = "${records.size}",
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
                                text = "${records.map { it.severity }.average()}",
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
                                text = "${
                                    records.map { Duration.between(it.startTime, it.endTime).toMillis() }.average()
                                }",
                                style = Typography.titleLarge,
                                color = Color(0xFF9810fa)
                            )
                            Text(
                                text = "Average Duration",
                                style = Typography.titleSmall,
                                color = Gray
                            )
                        }
                    }
                }




                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = cardColors,
                    border = BorderStroke(1.dp, LightGray)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .padding(24.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Panic Attack Calendar",
                                style = Typography.titleLarge,
                                color = Black
                            )
                        }
                    }
                }
            }
        }
    }
}