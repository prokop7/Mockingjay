package com.kazanexpress.mockserver.postman.model

class FolderItem(
    name: String,
    val item: List<Item>
) : Item(name)