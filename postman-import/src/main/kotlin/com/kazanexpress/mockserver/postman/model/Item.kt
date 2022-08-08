package com.kazanexpress.mockserver.postman.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes(
    value = [
        JsonSubTypes.Type(value = FolderItem::class, name = "folder"),
        JsonSubTypes.Type(value = RequestItem::class, name = "request")
    ]
)
abstract class Item(
    val name: String
)