package com.kazanexpress.mockserver.postman

import com.kazanexpress.mockserver.postman.model.*
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.nativex.hint.TypeAccess.*
import org.springframework.nativex.hint.TypeHint

@TypeHint(
    types = [
        Auth::class,
        Body::class,
        Basic::class,
        FolderItem::class,
        Header::class,
        ImportRs::class,
        Options::class,
        PostmanCollection::class,
        Query::class,
        Raw::class,
        Request::class,
        Response::class,
        Url::class,
        RequestItem::class,
        Variable::class,
        java.util.HashSet::class
    ],
    typeNames = ["kotlin.internal.jdk8.JDK8PlatformImplementations"],
    access = [
        DECLARED_FIELDS,
        DECLARED_CONSTRUCTORS,
        DECLARED_METHODS,
        DECLARED_CLASSES,
        PUBLIC_FIELDS,
        PUBLIC_CONSTRUCTORS,
        PUBLIC_METHODS,
        PUBLIC_CLASSES
    ]
)
@ComponentScan
@Configuration
class PostmanImportConfig {
}