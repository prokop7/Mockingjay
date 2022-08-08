package com.kazanexpress.mockserver.postman.model


data class Url(
    val host: List<String>,
    val path: List<String>,
    val port: String?,
    val query: List<Query> = listOf(),
    val raw: String

) {

    fun contextPath(): String {
        return "/" + path.joinToString("/")
    }
}