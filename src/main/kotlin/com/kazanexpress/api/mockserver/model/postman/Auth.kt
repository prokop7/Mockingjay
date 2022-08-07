package com.kazanexpress.api.mockserver.model.postman


data class Auth(
    val basic: List<Basic>,
    val type: String
)