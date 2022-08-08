package com.kazanexpress.mockserver.service

import com.kazanexpress.mockserver.model.Request
import com.kazanexpress.mockserver.model.Response
import com.kazanexpress.mockserver.service.listener.RequestListener
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class RequestHandler(
    private val mockDataService: MockDataService, private val requestListeners: List<RequestListener>
) {

    private val logger = KotlinLogging.logger {}

    fun handleRequest(
        mockId: String, request: ServerHttpRequest
    ): Mono<ResponseEntity<Any>> {
        val path = request.path.subPath(2).toString()
        logger.info { "Mock request for $mockId, path: $path" }
        return getBody(request).flatMap { body ->
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
        }.flatMap { rq ->
            mockDataService.handle(mockId, rq).doOnSuccess { response ->
                requestListeners.forEach { it.onComplete(mockId, rq, response) }
            }
        }.map(::toResponseEntity)
            .defaultIfEmpty(notFound())
    }

    private fun getBody(request: ServerHttpRequest): Mono<String> {
        val bodyMono = request.body.map {
            val bytes = ByteArray(it.capacity())
            it.read(bytes)
            String(bytes)
        }.next().switchIfEmpty(Mono.just(""))
        return bodyMono
    }

    private fun toResponseEntity(response: Response?): ResponseEntity<Any> {
        val httpHeaders = HttpHeaders()
        response ?: return notFound()
        response.headers.forEach { httpHeaders.add(it.key, it.value) }
        return ResponseEntity.status(response.status).headers(httpHeaders).body(response.body)
    }

    private fun notFound(): ResponseEntity<Any> = ResponseEntity.notFound().build()
}
