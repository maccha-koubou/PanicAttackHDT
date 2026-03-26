package com.example.panicattackhdt.model

enum class Symptom {
    RAPID_HEART_RATE,
    TREMBLING,
    SWEATING,
    CHEST_PAIN,
    DIZZINESS,
    NUMBNESS_TINGLING,
    HOT_FLUSH,
    COLD_CHILLS,
    FEAR_OF_DYING,
    NAUSEA,
    SHORTNESS_OF_BREATH,
    FEELINGS_OF_UNREALITY
}

fun List<String>.toSymptoms(): List<Symptom> {
    return this.mapNotNull { name ->
        try {
            Symptom.valueOf(name)
        } catch (e: Exception) {
            null
        }
    }
}