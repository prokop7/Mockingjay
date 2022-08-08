package com.kazanexpress.mockserver.mongo.dao

import com.kazanexpress.mockserver.mongo.model.MongoMockDataWrapper
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MockDataWrapperDao : ReactiveMongoRepository<MongoMockDataWrapper, String> {
}