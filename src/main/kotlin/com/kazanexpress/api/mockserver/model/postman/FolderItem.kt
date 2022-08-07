package com.kazanexpress.api.mockserver.model.postman

class FolderItem(
    name: String,
    val item: List<Item>
) : Item(name)