package com.kazanexpress.api.mockserver.model.postman


import com.fasterxml.jackson.annotation.JsonProperty

data class Response(
    val body: String,
    val code: Int,
    val cookie: Set<Any>,
    val header: Set<Header>,
    val name: String,
    val originalRequest: Request,
    @JsonProperty("_postman_previewlanguage")
    val postmanPreviewLanguage: String,
    val status: String
)