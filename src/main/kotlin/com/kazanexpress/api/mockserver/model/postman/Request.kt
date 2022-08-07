package com.kazanexpress.api.mockserver.model.postman


data class Request(
    val header: List<Header>,
    val method: String,
    val url: Url,
    val body: Body?
)