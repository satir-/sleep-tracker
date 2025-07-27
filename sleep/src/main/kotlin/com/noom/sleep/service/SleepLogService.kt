package com.noom.sleep.service

import com.noom.sleep.dto.SleepLogRequest
import com.noom.sleep.model.SleepLog
import com.noom.sleep.repository.SleepLogRepository

import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

@Service
class SleepLogService(
    private val sleepLogRepository: SleepLogRepository
) {

    fun createSleepLog(request: SleepLogRequest): SleepLog {
        //TODO: Consider go-to-bed and wake time have the same day (use timestamp?)
        val totalTime = Duration.between(
            request.bedTime.atDate(request.date),
            request.wakeTime.atDate(request.date.plusDays(1))
        ).toMinutes()

        val sleepLog = SleepLog(
            date = request.date,
            bedTime = request.bedTime,
            wakeTime = request.wakeTime,
            totalTimeMinutes = totalTime,
            mood = request.mood
        )

        return sleepLogRepository.save(sleepLog)
    }

    fun getLastSleepLog(): SleepLog? {
        // TODO: Remove hardcode
        return sleepLogRepository.findFirstByUserIdOrderByDateDesc(userId = 1)
    }

    fun get30DayAverage(): Map<String, Any> {
        val thirtyDaysAgo = LocalDate.now().minusDays(30)
        // TODO: Remove hardcode
        val logs = sleepLogRepository.findAllByUserIdAndDateAfter(userId = 1, dateAfter = thirtyDaysAgo)

        if (logs.isEmpty()) return mapOf("message" to "No data available")

        val avgTotalTime = logs.map { it.totalTimeMinutes }.average()
        val avgBedTime = logs.map { it.bedTime.toSecondOfDay() }.average().toInt()
        val avgWakeTime = logs.map { it.wakeTime.toSecondOfDay() }.average().toInt()

        val moodFrequencies = logs.groupingBy { it.mood }.eachCount()

        return mapOf(
            "range" to "$thirtyDaysAgo to ${LocalDate.now()}",
            "averageTotalTimeInBedMinutes" to avgTotalTime,
            "averageBedTime" to LocalTime.ofSecondOfDay(avgBedTime.toLong()),
            "averageWakeTime" to LocalTime.ofSecondOfDay(avgWakeTime.toLong()),
            "moodFrequencies" to moodFrequencies
        )
    }
}