package com.kazanexpress.mockserver.config

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.context.annotation.ComponentScan

@ComponentScan(
    basePackages = [
        "com.kazanexpress.mockserver.service",
        "com.kazanexpress.mockserver.controller"
    ]
)
@AutoConfiguration
class MockServerConfiguration 