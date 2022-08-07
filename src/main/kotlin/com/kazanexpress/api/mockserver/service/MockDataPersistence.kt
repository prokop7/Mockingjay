package com.kazanexpress.api.mockserver.service

import com.kazanexpress.api.mockserver.dao.ItemDao
import com.kazanexpress.api.mockserver.dao.MockDataDao
import com.kazanexpress.api.mockserver.model.MockData
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class MockDataPersistence(
    private val mockDataDao: MockDataDao,
    private val itemDao: ItemDao
) {
    fun save(mockData: MockData): Mono<MockData> {
        return itemDao.saveAll(mockData.requests)
            .then(mockDataDao.save(mockData))
    }

    fun get(id: String): Mono<MockData> {
        return mockDataDao.findById(id)
    }
}