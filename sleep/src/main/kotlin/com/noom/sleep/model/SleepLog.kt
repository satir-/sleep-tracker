package com.noom.sleep.model

import com.noom.sleep.dto.Mood
import java.time.LocalDate
import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "sleep_logs")
data class SleepLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    // TODO: Remove hardcode
    val userId: Long = 1, // Hardcoded for now

    val date: LocalDate = LocalDate.now(),

    val bedTime: LocalTime = LocalTime.MIDNIGHT,

    val wakeTime: LocalTime = LocalTime.MIDNIGHT,

    val totalTimeMinutes: Long = 0,

    @Enumerated(EnumType.STRING)
    val mood: Mood = Mood.OK
)
