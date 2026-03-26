package com.example.panicattackhdt.service

import androidx.compose.ui.graphics.Color

fun getSeverityColor(severity: Int): Color {
    return when(severity) {
        in 1..3 -> Color(0xFF00d368)
        in 4..7 -> Color(0xFFffbf00)
        else -> Color(0xFFE40000)
    }
}