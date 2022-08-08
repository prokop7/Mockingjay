package com.kazanexpress.mockserver.mongo.model

import com.kazanexpress.mockserver.model.Request
import com.kazanexpress.mockserver.model.Response
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.time.LocalDateTime

@Document("request_log")
class MongoRequestLog(
    val mockId: String,
    val request: Request,
    val response: Response?,
    @Indexed
    val dateTime: LocalDateTime,
    @MongoId
    val id: String
) {
}
