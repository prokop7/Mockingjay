package com.kazanexpress.mockserver.memory

import com.kazanexpress.mockserver.audit.model.RequestLog
import com.kazanexpress.mockserver.audit.service.AuditPersistence
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue

@Service
class InMemoryAuditPersistenceService(
    private val requestLogMap: ConcurrentHashMap<String, ConcurrentLinkedQueue<RequestLog>> = ConcurrentHashMap(),
) : AuditPersistence {

    override fun save(request: RequestLog): Mono<RequestLog> {
        val requestLogQueue = requestLogMap.getOrPut(request.mockId) { ConcurrentLinkedQueue() }
        requestLogQueue.add(request)
        return request.toMono()
    }

    override fun getRequestLogs(
        mockId: String,
        from: LocalDateTime,
        to: LocalDateTime,
        page: Int,
        size: Int
    ): Flux<RequestLog> {
        val requestLogs = requestLogMap[mockId]?.filter {
            it.dateTime.isAfter(from) && it.dateTime.isBefore(to)
        } ?: return Flux.empty()
        return Flux.fromIterable(requestLogs)
    }
}
