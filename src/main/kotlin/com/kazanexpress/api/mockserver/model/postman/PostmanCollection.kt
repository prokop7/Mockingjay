package com.kazanexpress.api.mockserver.model.postman


data class PostmanCollection(
    val auth: Auth?,
    val info: Info,
    val item: List<Item>,
    val variable: List<Variable> = listOf()
)