package com.kazanexpress.mockserver.dynamodb.service

import com.kazanexpress.mockserver.audit.model.RequestLog
import com.kazanexpress.mockserver.audit.service.AuditPersistence
import com.kazanexpress.mockserver.dynamodb.dao.RequestLogDao
import com.kazanexpress.mockserver.dynamodb.model.DynamoRequestLog
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class DynamoAuditPersistenceService(
    private val requestLogDao: RequestLogDao
) : AuditPersistence {

    override fun save(request: RequestLog): Mono<RequestLog> {
        return Mono.just(requestLogDao.save(request.toMongo())).map { it.toRequestLog() }
    }

    override fun getRequestLogs(
        mockId: String,
        from: LocalDateTime,
        to: LocalDateTime,
        page: Int,
        size: Int
    ): Flux<RequestLog> {
        return Flux.fromIterable(
            requestLogDao.findAllByMockIdAndDateTimeBetween(
                mockId,
                from,
                to,
                Pageable.ofSize(size).withPage(page)
            )
        )
            .map { it.toRequestLog() }
    }
}

private fun DynamoRequestLog.toRequestLog(): RequestLog {
    return RequestLog(
        mockId = mockId,
        id = id,
        request = request,
        response = response,
        dateTime = dateTime.toLocalDateTime()
    )
}

private fun RequestLog.toMongo(): DynamoRequestLog {
    return DynamoRequestLog(
        id = id,
        mockId = mockId,
        request = request,
        response = response,
        dateTime = Timestamp.from(dateTime.toInstant(ZoneOffset.UTC))
    )
}
