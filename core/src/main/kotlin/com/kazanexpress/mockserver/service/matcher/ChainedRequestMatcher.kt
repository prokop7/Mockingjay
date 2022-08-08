package com.kazanexpress.mockserver.service.matcher

import com.kazanexpress.mockserver.model.Request
import mu.KotlinLogging
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class ChainedRequestMatcher(
    private val requestMatchers: List<RequestMatcher>
) : RequestMatcher {

    private val logger = KotlinLogging.logger {}

    /**
     * Returns value in range [0, 1] where 0 means no match and 1 means full match.
     */
    override fun match(
        request: Request,
        mockRequest: Request
    ): Double {
        val res = requestMatchers.sumOf { it.match(request, mockRequest) } / requestMatchers.size
        logger.info { "Match result     : $res, mock: $mockRequest" }
        return res
    }
}