package com.kazanexpress.api.mockserver.service

import com.kazanexpress.api.mockserver.model.Request
import com.kazanexpress.api.mockserver.model.Response
import com.kazanexpress.api.mockserver.service.matcher.RequestMatcher
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class RequestHandler(
    private val persistence: MockDataPersistence,
    private val requestMatcher: RequestMatcher
) {

    private val logger = KotlinLogging.logger {}

    fun handleRequest(
        mockId: String,
        requestMono: Mono<Request>
    ): Mono<ResponseEntity<Any>> {
        return persistence.get(mockId)
            .flatMap { mockData ->
                requestMono.map { request ->
                    val item = mockData.requests.find { it.request.path == request.path }
                    item?.responses
                        ?.maxBy {
                            requestMatcher.match(request!!, it.request)
                        }
                        .also {
                            if (it == null) {
                                logger.info { "No response found for request: $request" }
                            } else {
                                logger.info { "Matched request: '${it.name}' ${it.status}" }
                            }
                        }
                        .toResponseEntity()
                }
            }
            .defaultIfEmpty(notFound())
    }
}

private fun Response?.toResponseEntity(): ResponseEntity<Any> {
    val httpHeaders = HttpHeaders()
    this ?: return notFound()
    this.headers.forEach { httpHeaders.add(it.key, it.value) }
    return ResponseEntity.status(this.status)
        .headers(httpHeaders)
        .body(this.body)
}

private fun notFound(): ResponseEntity<Any> = ResponseEntity.notFound().build()
