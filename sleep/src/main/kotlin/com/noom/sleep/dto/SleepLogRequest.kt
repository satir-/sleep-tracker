package com.noom.sleep.dto

import java.time.LocalDateTime

data class SleepLogRequest(
    val bedDateTime: LocalDateTime,
    val wakeDateTime: LocalDateTime,
    val mood: Mood
)

enum class Mood {
    BAD, OK, GOOD
}
