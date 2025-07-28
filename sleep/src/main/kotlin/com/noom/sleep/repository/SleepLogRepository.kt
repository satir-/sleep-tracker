package com.noom.sleep.repository

import com.noom.sleep.model.SleepLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface SleepLogRepository : JpaRepository<SleepLog, Long> {
    fun findFirstByUserIdOrderByBedDateTimeDesc(userId: Long): SleepLog?
    fun findAllByUserIdAndBedDateTimeAfter(userId: Long, bedDateTime: LocalDateTime): List<SleepLog>
}
