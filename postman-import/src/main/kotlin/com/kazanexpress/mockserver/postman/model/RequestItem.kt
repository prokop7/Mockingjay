package com.kazanexpress.mockserver.postman.model

class RequestItem(
    name: String,
    val request: Request,
    val response: List<Response>
) : Item(name)
