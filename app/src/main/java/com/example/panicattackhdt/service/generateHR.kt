package com.example.panicattackhdt.service

import kotlin.math.sin

fun generateHR(t: Int): Int {
    val min = 60
    val max = 160
    val medium = (max + min) / 2f
    val halfWaveHeight = (max - min) / 2f
    val period = 300f

    return (medium + halfWaveHeight * sin(2 * Math.PI * t / period - Math.PI / 2)).toInt()
}