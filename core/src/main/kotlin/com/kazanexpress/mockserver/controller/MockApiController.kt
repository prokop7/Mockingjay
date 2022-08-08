package com.kazanexpress.mockserver.controller

import com.kazanexpress.mockserver.service.RequestHandler
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class MockApiController(
    private val requestHandler: RequestHandler
) {

    @RequestMapping("/{mockId}/**")
    fun get(
        @PathVariable mockId: String, request: ServerHttpRequest
    ): Mono<ResponseEntity<Any>> {
        return requestHandler.handleRequest(mockId, request)
    }

}

