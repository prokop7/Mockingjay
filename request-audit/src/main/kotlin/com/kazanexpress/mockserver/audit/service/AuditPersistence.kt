package com.kazanexpress.mockserver.audit.service

import com.kazanexpress.mockserver.audit.model.RequestLog
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

interface AuditPersistence {

    fun save(request: RequestLog): Mono<RequestLog>
    fun getRequestLogs(
        mockId: String, from: LocalDateTime, to: LocalDateTime, page: Int, size: Int
    ): Flux<RequestLog>

}