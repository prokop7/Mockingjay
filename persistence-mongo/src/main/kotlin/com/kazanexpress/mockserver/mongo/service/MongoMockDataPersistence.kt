package com.kazanexpress.mockserver.mongo.service

import com.kazanexpress.mockserver.model.MockData
import com.kazanexpress.mockserver.mongo.dao.MockDataWrapperDao
import com.kazanexpress.mockserver.mongo.model.MongoMockDataWrapper
import com.kazanexpress.mockserver.service.MockDataPersistence
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class MongoMockDataPersistence(
    private val mockDataWrapperDao: MockDataWrapperDao
) : MockDataPersistence {

    override fun save(mockData: MockData): Mono<MockData> {
        return mockDataWrapperDao.save(MongoMockDataWrapper(mockData)).map { it.data }
    }

    override fun get(id: String): Mono<MockData> {
        return mockDataWrapperDao.findById(id).map { it.data }
    }
}