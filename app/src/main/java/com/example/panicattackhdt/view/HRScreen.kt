package com.example.panicattackhdt.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.panicattackhdt.storage.DataStorage
import com.example.panicattackhdt.ui.theme.BackgroundGray
import com.example.panicattackhdt.ui.theme.Black
import com.example.panicattackhdt.ui.theme.Gray
import com.example.panicattackhdt.ui.theme.LightGray
import com.example.panicattackhdt.ui.theme.White
import com.example.panicattackhdt.ui.theme.cardColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.panicattackhdt.ui.theme.Typography
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.core.cartesian.CartesianMeasuringContext
import com.patrykandpatrick.vico.core.cartesian.axis.Axis
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.ColumnCartesianLayerModel
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.Defaults
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.core.common.Fill
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HRScreen(navController: NavController) {
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

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarColors(White, White, Gray, Black, Gray),
                title = {
                    Text("Heart Rate Patterns")
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
                .background(BackgroundGray)
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(16.dp)
            ) {
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
                                text = "Pattern Overview",
                                style = Typography.titleLarge,
                                color = Black
                            )
                        }
                        val dateFormatter = object : CartesianValueFormatter {
                            override fun format(
                                context: CartesianMeasuringContext,
                                value: Double,
                                verticalAxisPosition: Axis.Position.Vertical?
                            ): CharSequence {
                                val index = value.toInt()
                                return if (index in dateList.indices) dateList[index] else ""
                            }
                        }
                        CartesianChartHost(
                            rememberCartesianChart(
                                rememberColumnCartesianLayer(
                                    columnProvider = getValueBasedColumnProvider(),
                                ),
                                startAxis = VerticalAxis.rememberStart(),
                                bottomAxis = HorizontalAxis.rememberBottom(
                                    valueFormatter = dateFormatter,
                                ),
                            ),
                            modelProducer,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun getValueBasedColumnProvider() =
    object : ColumnCartesianLayer.ColumnProvider {
        val low = rememberLineComponent(
            fill = Fill(Color(0xFF00d368).toArgb()),
            thickness = 16.dp,
            shape = CorneredShape.rounded(16),
        )
        val medium = rememberLineComponent(
            fill = Fill(Color(0xFFffbf00).toArgb()),
            thickness = 16.dp,
            shape = CorneredShape.rounded(16),
        )
        val high = rememberLineComponent(
            fill = Fill(Color(0xFFE40000).toArgb()),
            thickness = 16.dp,
            shape = CorneredShape.rounded(16),
        )

        override fun getColumn(
            entry: ColumnCartesianLayerModel.Entry,
            seriesIndex: Int,
            extraStore: ExtraStore
        ) = when {
            entry.y < 100 -> low
            entry.y < 150 -> medium
            else -> high
        }
        override fun getWidestSeriesColumn(seriesIndex: Int, extraStore: ExtraStore) = high
    }