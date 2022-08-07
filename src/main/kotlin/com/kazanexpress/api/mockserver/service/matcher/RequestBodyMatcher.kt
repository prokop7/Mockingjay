package com.kazanexpress.api.mockserver.service.matcher

import com.kazanexpress.api.mockserver.model.Request
import com.kazanexpress.api.mockserver.similarity
import mu.KotlinLogging
import org.springframework.stereotype.Service


@Service
class RequestBodyMatcher : RequestMatcher {

    private val logger = KotlinLogging.logger {}

    override fun match(
        request: Request,
        mockRequest: Request
    ): Double {
        val requestBody = request.body
        val mockBody = mockRequest.body
        val res = similarity(requestBody, mockBody)
        logger.info { "Body similarity          : $res" }
        return res
    }

}