package com.kazanexpress.mockserver.mongo.service

import com.kazanexpress.mockserver.audit.model.RequestLog
import com.kazanexpress.mockserver.audit.service.AuditPersistence
import com.kazanexpress.mockserver.mongo.dao.RequestLogDao
import com.kazanexpress.mockserver.mongo.model.MongoRequestLog
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class MongoAuditPersistenceService(
    private val requestLogDao: RequestLogDao
) : AuditPersistence {

    override fun save(request: RequestLog): Mono<RequestLog> {
        return requestLogDao.save(request.toMongo()).map { it.toRequestLog() }
    }

    override fun getRequestLogs(
        mockId: String,
        from: LocalDateTime,
        to: LocalDateTime,
        page: Int,
        size: Int
    ): Flux<RequestLog> {
        return requestLogDao.findAll(mockId, from, to, Pageable.ofSize(size).withPage(page))
            .map { it.toRequestLog() }
    }
}

private fun MongoRequestLog.toRequestLog(): RequestLog {
    return RequestLog(
        mockId = mockId,
        id = id,
        request = request,
        response = response,
        dateTime = dateTime
    )
}

private fun RequestLog.toMongo(): MongoRequestLog {
    return MongoRequestLog(
        mockId = mockId,
        id = id,
        request = request,
        response = response,
        dateTime = dateTime
    )
}
