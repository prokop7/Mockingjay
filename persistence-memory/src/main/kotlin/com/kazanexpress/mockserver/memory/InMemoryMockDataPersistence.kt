package com.kazanexpress.mockserver.memory

import com.kazanexpress.mockserver.model.MockData
import com.kazanexpress.mockserver.service.MockDataPersistence
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.concurrent.ConcurrentHashMap

@Service
class InMemoryMockDataPersistence(
    private val mockDataMap: ConcurrentHashMap<String, MockData> = ConcurrentHashMap(),
) : MockDataPersistence {

    override fun save(mockData: MockData): Mono<MockData> {
        mockDataMap[mockData.id] = mockData
        return Mono.just(mockData)
    }

    override fun get(id: String): Mono<MockData> {
        return Mono.justOrEmpty(mockDataMap[id])
    }
}