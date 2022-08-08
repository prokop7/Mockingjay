package com.kazanexpress.mockserver.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MockServerApplication

fun main(args: Array<String>) {
    runApplication<MockServerApplication>(*args)
}
