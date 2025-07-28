package com.noom.sleep.service

import com.noom.sleep.dto.SleepAverageResponse
import com.noom.sleep.dto.SleepLogRequest
import com.noom.sleep.model.SleepLog
import com.noom.sleep.repository.SleepLogRepository

import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class SleepLogService(
    private val sleepLogRepository: SleepLogRepository
) {

    fun createSleepLog(request: SleepLogRequest): SleepLog {
        val totalTime = Duration.between(
            request.bedDateTime,
            request.wakeDateTime
        ).toMinutes()

        if (totalTime <= 0) {
            throw IllegalArgumentException("Wake time must be after bed time")
        }

        val sleepLog = SleepLog(
            bedDateTime = request.bedDateTime,
            wakeDateTime = request.wakeDateTime,
            totalTimeMinutes = totalTime,
            mood = request.mood
        )

        return sleepLogRepository.save(sleepLog)
    }

    fun getLastSleepLog(userId: Long = 1): SleepLog? {
        // Use bedDateTime for filtering
        return sleepLogRepository.findFirstByUserIdOrderByBedDateTimeDesc(userId)
    }

    fun get30DayAverage(userId: Long = 1): SleepAverageResponse? {
        val today = LocalDateTime.now()
        val thirtyDaysAgo = today.minusDays(30)

        // Use bedDateTime for filtering
        val logs = sleepLogRepository.findAllByUserIdAndBedDateTimeAfter(userId, thirtyDaysAgo)

        if (logs.isEmpty()) return null

        val avgTotalTime = logs.map { it.totalTimeMinutes }.average()
        val avgBedTime = logs.map { it.bedDateTime.toLocalTime().toSecondOfDay() }.average().toInt()
        val avgWakeTime = logs.map { it.wakeDateTime.toLocalTime().toSecondOfDay() }.average().toInt()

        val moodFrequencies = logs.groupingBy { it.mood }.eachCount()

        return SleepAverageResponse(
            "$thirtyDaysAgo to $today",
            avgTotalTime,
            LocalTime.ofSecondOfDay(avgBedTime.toLong()),
            LocalTime.ofSecondOfDay(avgWakeTime.toLong()),
            moodFrequencies
        )
    }
}