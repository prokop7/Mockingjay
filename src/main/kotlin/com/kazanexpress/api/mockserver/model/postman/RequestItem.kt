package com.kazanexpress.api.mockserver.model.postman

class RequestItem(
    name: String,
    val request: Request,
    val response: List<Response>
) : Item(name)
