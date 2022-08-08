package com.kazanexpress.mockserver.postman.service

import com.kazanexpress.mockserver.model.CollectionItem
import com.kazanexpress.mockserver.model.MockData
import com.kazanexpress.mockserver.model.Request
import com.kazanexpress.mockserver.model.Response
import com.kazanexpress.mockserver.postman.model.*
import com.kazanexpress.mockserver.service.MockDataPersistence
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import com.kazanexpress.mockserver.postman.model.Request as PostmanRequest
import com.kazanexpress.mockserver.postman.model.Response as PostmanResponse

@Service
class PostmanImportService(
    private val persistence: MockDataPersistence
) {
    private fun parseItems(items: List<Item>): List<CollectionItem> {
        val res = mutableListOf<CollectionItem>()
        for (item in items) {
            if (item is FolderItem) {
                res.addAll(parseItems(item.item))
            } else if (item is RequestItem) {
                res.add(
                    CollectionItem(
                        item.request.map(),
                        item.response.map(::map).toSet(),
                        item.name
                    )
                )
            }
        }
        return res
    }

    fun importPostmanCollection(collection: PostmanCollection): Mono<ImportRs> {
        val mockData = MockData(
            collection.info.name,
            parseItems(collection.item)
        )
        return persistence.save(mockData).map {
            ImportRs(it.id)
        }
    }

    private fun map(it: PostmanResponse) =
        Response(
            it.name,
            it.originalRequest.map(),
            it.code,
            it.body,
            it.header.associate { it.key to it.value },
        )
}

private fun PostmanRequest.map(): Request {
    return Request(
        this.url.contextPath(),
        this.method,
        this.body?.raw ?: "",
        this.url.query.associate { it.key to it.value },
        this.header.associate { it.key to it.value },
    )
}
