package com.kazanexpress.api.mockserver.model

import com.kazanexpress.api.mockserver.randomString
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.time.LocalDateTime
import java.util.*

@Document
class MockData(
    val name: String,
    val requests: List<CollectionItem>,
    @MongoId
    val id: String = randomString(20),
    val createdDate: LocalDateTime = LocalDateTime.now()
) {
}

class CollectionItem(
    val request: Request,
    val responses: Set<Response>,
    val name: String,
    val id: String = UUID.randomUUID().toString()
) {
}

class Request(
    val path: String,
    val method: String,
    val body: String = "",
    val queryParams: Map<String, String> = emptyMap(),
    val headers: Map<String, String> = emptyMap(),
) {
    override fun toString(): String {
        return "'$method': '$path'"
    }
}

class Response(
    val name: String,
    val request: Request,
    val status: Int,
    val body: String?,
    val headers: Map<String, String>,
)
