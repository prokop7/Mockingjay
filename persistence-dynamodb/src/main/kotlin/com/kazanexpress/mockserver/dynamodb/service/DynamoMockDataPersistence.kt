package com.kazanexpress.mockserver.dynamodb.service

import com.kazanexpress.mockserver.dynamodb.dao.MockDataWrapperDao
import com.kazanexpress.mockserver.dynamodb.model.DynamoMockDataWrapper
import com.kazanexpress.mockserver.model.MockData
import com.kazanexpress.mockserver.service.MockDataPersistence
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class DynamoMockDataPersistence(
    private val mockDataWrapperDao: MockDataWrapperDao
) : MockDataPersistence {

    override fun save(mockData: MockData): Mono<MockData> {
        return Mono.just(mockDataWrapperDao.save(DynamoMockDataWrapper(mockData))).map { it.data }
    }

    override fun get(id: String): Mono<MockData> {
        return Mono.justOrEmpty(mockDataWrapperDao.findById(id)).map { it.data }
    }
}