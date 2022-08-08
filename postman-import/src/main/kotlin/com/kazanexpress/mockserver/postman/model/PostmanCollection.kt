package com.kazanexpress.mockserver.postman.model


data class PostmanCollection(
    val auth: Auth?,
    val info: Info,
    val item: List<Item>,
    val variable: List<Variable> = listOf()
)