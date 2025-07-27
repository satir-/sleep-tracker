package com.noom.sleep.model

import com.noom.sleep.dto.Mood
import java.time.LocalDate
import java.time.LocalTime
import jakarta.persistence.*

@Entity
@Table(name = "sleep_logs")
data class SleepLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    // TODO: Remove hardcode
    val userId: Long = 1, // Hardcoded for now

    val date: LocalDate,

    val bedTime: LocalTime,

    val wakeTime: LocalTime,

    val totalTimeMinutes: Long,

    @Enumerated(EnumType.STRING)
    val mood: Mood
)
