package com.kazanexpress.api.mockserver.model.postman


import com.fasterxml.jackson.annotation.JsonProperty

data class Info(
    @JsonProperty("_exporter_id")
    val exporterId: String,
    val name: String,
    @JsonProperty("_postman_id")
    val postmanId: String,
    val schema: String
)