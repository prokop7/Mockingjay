package com.kazanexpress.mockserver.mongo.dao

import com.kazanexpress.mockserver.mongo.model.MongoRequestLog
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.LocalDateTime

@Repository
interface RequestLogDao : ReactiveMongoRepository<MongoRequestLog, String> {

    @Query(
        value = "{ 'dateTime': { '\$gte': ?1, '\$lte': ?2 }, 'mockId': { '\$eq': ?0 } }",
        sort = "{ dateTime: -1 }",
    )
    fun findAll(
        mockId: String,
        from: LocalDateTime,
        to: LocalDateTime,
        pageable: Pageable
    ): Flux<MongoRequestLog>
}