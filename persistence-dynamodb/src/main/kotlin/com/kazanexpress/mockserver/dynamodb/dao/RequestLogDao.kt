package com.kazanexpress.mockserver.dynamodb.dao

import com.kazanexpress.mockserver.dynamodb.model.DynamoRequestLog
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@EnableScan
@Repository
interface RequestLogDao : DynamoDBCrudRepository<DynamoRequestLog, String> {

    fun findAllByMockIdAndDateTimeBetween(
        mockId: String,
        from: LocalDateTime,
        to: LocalDateTime,
        pageable: Pageable
    ): List<DynamoRequestLog>
}