package com.example.panicattackhdt.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class RecordData(
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val severity: Int,
    val symptoms: List<Symptom>,
    val trigger: String,
    val location: String,
    val notes: String,
    val peak: Int
)
@Serializable
data class RecordDataJson(
    val startTime: String,
    val endTime: String,
    val severity: Int,
    val symptoms: List<String>,
    val trigger: String,
    val location: String,
    val notes: String,
    val peak: Int
)

fun RecordData.toJsonData(): RecordDataJson {
    return RecordDataJson(
        startTime = this.startTime.toString(),
        endTime = this.endTime.toString(),
        severity = this.severity,
        symptoms = this.symptoms.map { it.name },
        trigger = this.trigger,
        location = this.location,
        notes = this.notes,
        peak = this.peak
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun RecordDataJson.toRecordData(): RecordData {
    return RecordData(
        startTime = LocalDateTime.parse(this.startTime),
        endTime = LocalDateTime.parse(this.endTime),
        severity = this.severity,
        symptoms = this.symptoms.toSymptoms(),
        trigger = this.trigger,
        location = this.location,
        notes = this.notes,
        peak = this.peak
    )
}