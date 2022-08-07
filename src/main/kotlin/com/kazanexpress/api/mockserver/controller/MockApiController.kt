package com.kazanexpress.api.mockserver.controller

import com.kazanexpress.api.mockserver.model.Request
import com.kazanexpress.api.mockserver.service.RequestHandler
import mu.KotlinLogging
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

    private val logger = KotlinLogging.logger {}

    @RequestMapping("/{mockId}/**")
    fun get(
        @PathVariable mockId: String, request: ServerHttpRequest
    ): Mono<ResponseEntity<Any>> {
        val path = request.path.subPath(2).toString()
        logger.info { "Mock request for $mockId, path: $path" }
        val bodyMono = getBody(request)
        val inRequest: Mono<Request> = bodyMono.flatMap { body ->
            Mono.just(
                Request(
                    path,
                    request.method.name(),
                    body,
                    request.queryParams.toSingleValueMap(),
                    request.headers.toSingleValueMap()
                )
            )
        }.doOnSuccess {
            logger.info { "Request: $it" }
        }
        return requestHandler.handleRequest(mockId, inRequest)
    }

    private fun getBody(request: ServerHttpRequest): Mono<String> {
        val bodyMono = request.body.map {
            val bytes = ByteArray(it.capacity())
            it.read(bytes)
            String(bytes)
        }.next().switchIfEmpty(Mono.just(""))
        return bodyMono
    }

}

