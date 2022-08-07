package com.kazanexpress.api.mockserver.model.postman


data class Body(
    val mode: String,
    val options: Options?,
    val raw: String?
)