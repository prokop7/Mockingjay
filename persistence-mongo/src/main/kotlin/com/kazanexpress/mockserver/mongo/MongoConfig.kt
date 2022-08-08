package com.kazanexpress.mockserver.mongo

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories


@EntityScan(basePackages = ["com.kazanexpress.mockserver.mongo.model"])
@ComponentScan(
    basePackages = [
        "com.kazanexpress.mockserver.mongo.dao",
        "com.kazanexpress.mockserver.mongo.service"
    ]
)
@EnableReactiveMongoRepositories(basePackages = ["com.kazanexpress.mockserver.mongo.dao"])
@Configuration
class MongoConfig 