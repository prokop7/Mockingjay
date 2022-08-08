package com.kazanexpress.mockserver.postman.model


data class Request(
    val header: List<Header>,
    val method: String,
    val url: Url,
    val body: Body?
)