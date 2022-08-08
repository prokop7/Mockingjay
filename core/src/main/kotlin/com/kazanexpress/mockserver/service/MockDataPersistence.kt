package com.kazanexpress.mockserver.service

import com.kazanexpress.mockserver.model.MockData
import reactor.core.publisher.Mono

interface MockDataPersistence {
    fun save(mockData: MockData): Mono<MockData>
    fun get(id: String): Mono<MockData>
}