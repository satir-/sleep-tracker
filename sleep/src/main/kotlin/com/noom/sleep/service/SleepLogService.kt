package com.noom.sleep.service

import com.noom.sleep.dto.SleepLogRequest
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class SleepLogService() {
    val result = "Test response"

    fun createSleepLog(request: SleepLogRequest): ResponseEntity<Any> {
        println("Create sleep log with request: $request")
        return ResponseEntity.ok(result)
    }

    fun getLastSleepLog(): ResponseEntity<Any> {
        println("Getting last sleep log")
        return ResponseEntity.ok(result)
    }

    fun get30DayAverage(): ResponseEntity<Any> {
        println("Getting 30 day average with request")
        return ResponseEntity.ok(result)
    }
}