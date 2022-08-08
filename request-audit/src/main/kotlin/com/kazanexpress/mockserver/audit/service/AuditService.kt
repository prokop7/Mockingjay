package com.kazanexpress.mockserver.audit.service

import com.kazanexpress.mockserver.audit.model.RequestLog
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.LocalDateTime

@Service
class AuditService(
    private val auditPersistence: AuditPersistence
) {

    fun getLogs(
        mockId: String,
        from: LocalDateTime,
        to: LocalDateTime,
        page: Int,
        size: Int
    ): Flux<RequestLog> {
        return auditPersistence.getRequestLogs(mockId, from, to, page, size)
    }
}