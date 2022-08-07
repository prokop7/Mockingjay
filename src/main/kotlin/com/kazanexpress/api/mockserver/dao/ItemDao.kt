package com.kazanexpress.api.mockserver.dao

import com.kazanexpress.api.mockserver.model.CollectionItem
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemDao : ReactiveMongoRepository<CollectionItem, String> {
}