package com.noom.sleep.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.noom.sleep.dto.Mood
import com.noom.sleep.dto.SleepAverageResponse
import com.noom.sleep.dto.SleepLogRequest
import com.noom.sleep.model.SleepLog
import com.noom.sleep.service.SleepLogService

import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.time.LocalDateTime
import java.time.LocalTime

@WebMvcTest(SleepLogController::class)
class SleepLogControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var sleepLogService: SleepLogService

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `POST sleep-log should return 200 and saved log`() {
        val request = SleepLogRequest(
            bedDateTime = LocalDateTime.of(2025, 7, 28, 23, 0),
            wakeDateTime = LocalDateTime.of(2025, 7, 29, 7, 0),
            mood = Mood.GOOD
        )

        val saved = SleepLog(
            id = 1,
            bedDateTime = request.bedDateTime,
            wakeDateTime = request.wakeDateTime,
            totalTimeMinutes = 480,
            mood = Mood.GOOD
        )

        every { sleepLogService.createSleepLog(any()) } returns saved

        mockMvc.post("/sleep-log") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
            jsonPath("$.id") { value(1) }
            jsonPath("$.totalTimeMinutes") { value(480) }
            jsonPath("$.mood") { value("GOOD") }
        }
    }

    @Test
    fun `GET sleep-log last should return latest log`() {
        val latest = SleepLog(
            id = 2,
            bedDateTime = LocalDateTime.of(2025, 7, 27, 23, 30),
            wakeDateTime = LocalDateTime.of(2025, 7, 28, 7, 30),
            totalTimeMinutes = 480,
            mood = Mood.OK
        )

        every { sleepLogService.getLastSleepLog(1) } returns latest

        mockMvc.get("/sleep-log/last")
            .andExpect {
                status { isOk() }
                jsonPath("$.id") { value(2) }
                jsonPath("$.mood") { value("OK") }
            }
    }

    @Test
    fun `GET sleep-log last should return 404 if no log exists`() {
        every { sleepLogService.getLastSleepLog(1) } returns null

        mockMvc.get("/sleep-log/last")
            .andExpect {
                status { isNotFound() }
                jsonPath("$.message") { value("No sleep log found") }
            }
    }

    @Test
    fun `GET sleep-log average should return data if available`() {
        val response = SleepAverageResponse(
            range = "2025-06-28 to 2025-07-28",
            averageTotalTimeInBedMinutes = 465.0,
            averageBedTime = LocalTime.of(23, 0),
            averageWakeTime = LocalTime.of(7, 30),
            moodFrequencies = mapOf(Mood.GOOD to 2, Mood.OK to 1)
        )

        every { sleepLogService.get30DayAverage(1) } returns response

        mockMvc.get("/sleep-log/average")
            .andExpect {
                status { isOk() }
                jsonPath("$.averageTotalTimeInBedMinutes") { value(465.0) }
                jsonPath("$.moodFrequencies.GOOD") { value(2) }
            }
    }

    @Test
    fun `GET sleep-log average should return 404 if no data`() {
        every { sleepLogService.get30DayAverage(1) } returns null

        mockMvc.get("/sleep-log/average")
            .andExpect {
                status { isNotFound() }
                jsonPath("$.message") { value("No sleep data for last 30 days") }
            }
    }
}