package com.kazanexpress.api.mockserver.dao

import com.kazanexpress.api.mockserver.model.MockData
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MockDataDao : ReactiveMongoRepository<MockData, String> {
}