package com.kazanexpress.mockserver.postman.model


data class Body(
    val mode: String,
    val options: Options?,
    val raw: String?
)