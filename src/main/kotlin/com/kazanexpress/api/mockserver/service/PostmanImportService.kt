package com.kazanexpress.api.mockserver.service

import com.kazanexpress.api.mockserver.dto.ImportRs
import com.kazanexpress.api.mockserver.model.CollectionItem
import com.kazanexpress.api.mockserver.model.MockData
import com.kazanexpress.api.mockserver.model.Request
import com.kazanexpress.api.mockserver.model.Response
import com.kazanexpress.api.mockserver.model.postman.FolderItem
import com.kazanexpress.api.mockserver.model.postman.Item
import com.kazanexpress.api.mockserver.model.postman.PostmanCollection
import com.kazanexpress.api.mockserver.model.postman.RequestItem
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import com.kazanexpress.api.mockserver.model.postman.Request as PostmanRequest

private fun PostmanRequest.map(): Request {
    return Request(
        this.url.contextPath(),
        this.method,
        this.body?.raw ?: "",
        this.url.query.associate { it.key to it.value },
        this.header.associate { it.key to it.value },
    )
}

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

    private fun map(it: com.kazanexpress.api.mockserver.model.postman.Response) =
        Response(
            it.name,
            it.originalRequest.map(),
            it.code,
            it.body,
            it.header.associate { it.key to it.value },
        )
}