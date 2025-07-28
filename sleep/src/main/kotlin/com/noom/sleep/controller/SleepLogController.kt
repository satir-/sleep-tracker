package com.noom.sleep.controller

import com.noom.sleep.dto.SleepLogRequest
import com.noom.sleep.service.SleepLogService

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sleep-log")
class SleepLogController(
    private val sleepLogService: SleepLogService
) {
    @PostMapping
    fun createSleepLog(@RequestBody request: SleepLogRequest): ResponseEntity<Any> {
        val result = sleepLogService.createSleepLog(request)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/last")
    fun getLastNightLog(): ResponseEntity<Any> {
        val result = sleepLogService.getLastSleepLog()
        return result?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.status(404).body(mapOf("message" to "No sleep log found"))
    }

    @GetMapping("/average")
    fun get30DayAverage(): ResponseEntity<Any> {
        val result = sleepLogService.get30DayAverage()
        return result?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.status(404).body(mapOf("message" to "No sleep data for last 30 days"))
    }
}