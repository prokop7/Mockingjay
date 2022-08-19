package com.kazanexpress.mockserver.config

import com.kazanexpress.mockserver.model.CollectionItem
import com.kazanexpress.mockserver.model.MockData
import com.kazanexpress.mockserver.model.Request
import com.kazanexpress.mockserver.model.Response
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.nativex.hint.TypeAccess
import org.springframework.nativex.hint.TypeHint


@TypeHint(
    types = [MockData::class, CollectionItem::class, Request::class, Response::class],
    access = [
        TypeAccess.DECLARED_FIELDS,
        TypeAccess.DECLARED_CONSTRUCTORS,
        TypeAccess.DECLARED_METHODS,
        TypeAccess.DECLARED_CLASSES,
        TypeAccess.PUBLIC_FIELDS,
        TypeAccess.PUBLIC_CONSTRUCTORS,
        TypeAccess.PUBLIC_METHODS,
        TypeAccess.PUBLIC_CLASSES
    ]
)
@ComponentScan(
    basePackages = [
        "com.kazanexpress.mockserver.service",
        "com.kazanexpress.mockserver.controller"
    ]
)
@AutoConfiguration
class MockServerConfiguration 