package com.noom.sleep.model

import com.noom.sleep.dto.Mood
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "sleep_logs")
data class SleepLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val userId: Long = 1,

    val bedDateTime: LocalDateTime = LocalDateTime.now(),

    val wakeDateTime: LocalDateTime = LocalDateTime.now(),

    val totalTimeMinutes: Long = 0,

    @Enumerated(EnumType.STRING)
    val mood: Mood = Mood.OK
)
