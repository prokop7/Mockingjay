package com.kazanexpress.mockserver.mongo.model

import com.kazanexpress.mockserver.model.MockData
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.time.LocalDateTime

@Document("mock_data")
class MongoMockDataWrapper(
    val data: MockData,
    @MongoId val id: String = data.id
) {
    @CreatedDate
    var createdDate: LocalDateTime? = null
}