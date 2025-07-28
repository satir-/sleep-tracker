package com.noom.sleep.dto

import java.time.LocalTime

data class SleepAverageResponse(
    val range: String,
    val averageTotalTimeInBedMinutes: Double,
    val averageBedTime: LocalTime,
    val averageWakeTime: LocalTime,
    val moodFrequencies: Map<Mood, Int>
)