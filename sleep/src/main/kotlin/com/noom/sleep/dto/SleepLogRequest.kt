package com.noom.sleep.dto

import java.time.LocalDate
import java.time.LocalTime

data class SleepLogRequest(
    val date: LocalDate,
    val bedTime: LocalTime,
    val wakeTime: LocalTime,
    val mood: Mood
)

enum class Mood {
    BAD, OK, GOOD
}
