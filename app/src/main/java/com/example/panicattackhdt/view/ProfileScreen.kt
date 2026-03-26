package com.example.panicattackhdt.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.panicattackhdt.ui.theme.BackgroundGray
import com.example.panicattackhdt.ui.theme.Typography


@Preview
@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .background(BackgroundGray)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Coming soon...",
            style = Typography.titleSmall
        )
    }
}