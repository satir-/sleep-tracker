package com.noom.sleep.repository

import com.noom.sleep.model.SleepLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface SleepLogRepository : JpaRepository<SleepLog, Long> {
    fun findFirstByUserIdOrderByDateDesc(userId: Long): SleepLog?
    fun findAllByUserIdAndDateAfter(userId: Long, dateAfter: LocalDate): List<SleepLog>
}
