package com.kazanexpress.mockserver.service

import com.kazanexpress.mockserver.model.Request
import com.kazanexpress.mockserver.model.Response
import com.kazanexpress.mockserver.service.matcher.RequestMatcher
import mu.KotlinLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class MockDataService(
    private val persistence: MockDataPersistence,
    private val requestMatcher: RequestMatcher
) {
    private val logger = KotlinLogging.logger {}

    fun handle(mockId: String, request: Request): Mono<Response?> {
        return persistence.get(mockId)
            .mapNotNull { mockData ->
                mockData.requests
                    .find { it.request.path == request.path }
                    ?.responses
                    ?.maxBy {
                        requestMatcher.match(request, it.request)
                    }
            }
    }

}