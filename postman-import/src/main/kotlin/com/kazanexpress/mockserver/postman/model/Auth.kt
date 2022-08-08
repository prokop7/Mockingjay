package com.kazanexpress.mockserver.postman.model


data class Auth(
    val basic: List<Basic>,
    val type: String
)