package com.noom.sleep.service

import com.noom.sleep.dto.Mood
import com.noom.sleep.dto.SleepLogRequest
import com.noom.sleep.model.SleepLog
import com.noom.sleep.repository.SleepLogRepository

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class SleepLogServiceTest {

    private val repository = mockk<SleepLogRepository>(relaxed = true)
    private val service = SleepLogService(repository)

    @Test
    fun `createSleepLog should calculate total time and save log`() {
        val bedTime = LocalDateTime.of(2025, 7, 27, 23, 0)
        val wakeTime = LocalDateTime.of(2025, 7, 28, 7, 0)

        val request = SleepLogRequest(
            bedDateTime = bedTime,
            wakeDateTime = wakeTime,
            mood = Mood.GOOD
        )

        val savedLog = SleepLog(
            id = 1,
            bedDateTime = bedTime,
            wakeDateTime = wakeTime,
            totalTimeMinutes = 480,
            mood = Mood.GOOD
        )

        every { repository.save(any()) } returns savedLog

        val result = service.createSleepLog(request)

        assertEquals(480, result.totalTimeMinutes)
        assertEquals(Mood.GOOD, result.mood)
        verify { repository.save(any()) }
    }

    @Test
    fun `createSleepLog should throw exception if wake time is before bed time`() {
        val request = SleepLogRequest(
            bedDateTime = LocalDateTime.of(2025, 7, 29, 8, 0),
            wakeDateTime = LocalDateTime.of(2025, 7, 29, 7, 0), // earlier!
            mood = Mood.BAD
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            service.createSleepLog(request)
        }

        assertEquals("Wake time must be after bed time", exception.message)
    }

    @Test
    fun `getLastSleepLog should return the latest sleep log when it exists`() {
        val latestLog = SleepLog(
            id = 10,
            bedDateTime = LocalDateTime.of(2025, 7, 28, 23, 0),
            wakeDateTime = LocalDateTime.of(2025, 7, 29, 7, 0),
            totalTimeMinutes = 480,
            mood = Mood.GOOD
        )

        every { repository.findFirstByUserIdOrderByBedDateTimeDesc(1) } returns latestLog

        val result = service.getLastSleepLog()

        assertNotNull(result)
        assertEquals(10, result?.id)
        assertEquals(Mood.GOOD, result?.mood)
    }

    @Test
    fun `getLastSleepLog should return null if repository returns null`() {
        every { repository.findFirstByUserIdOrderByBedDateTimeDesc(1) } returns null

        val result = service.getLastSleepLog()

        assertNull(result)
    }

    @Test
    fun `get30DayAverage should return correct average when logs exist`() {
        val now = LocalDateTime.now()

        val logs = listOf(
            SleepLog(bedDateTime = now.minusDays(1), wakeDateTime = now, totalTimeMinutes = 480, mood = Mood.GOOD),
            SleepLog(
                bedDateTime = now.minusDays(2),
                wakeDateTime = now.minusDays(1),
                totalTimeMinutes = 450,
                mood = Mood.OK
            ),
            SleepLog(
                bedDateTime = now.minusDays(3),
                wakeDateTime = now.minusDays(2),
                totalTimeMinutes = 420,
                mood = Mood.BAD
            )
        )

        every { repository.findAllByUserIdAndBedDateTimeAfter(1, any()) } returns logs

        val average = service.get30DayAverage()

        assertNotNull(average)
        assertEquals(3, average?.moodFrequencies?.values?.sum())
        assertEquals(450.0, average?.averageTotalTimeInBedMinutes)
    }

    @Test
    fun `get30DayAverage should return null if no logs exist`() {
        every { repository.findAllByUserIdAndBedDateTimeAfter(1, any()) } returns emptyList()

        val average = service.get30DayAverage()

        assertNull(average)
    }
}