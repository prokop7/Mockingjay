package com.kazanexpress.mockserver.service.listener

import com.kazanexpress.mockserver.model.Request
import com.kazanexpress.mockserver.model.Response
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class LoggingRequestListener : RequestListener {

    private val logger = KotlinLogging.logger {}

    override fun onComplete(mockId: String, request: Request, response: Response?) {
        if (response != null) {
            logger.info { "Mock $mockId: Request: ${request.method} ${request.path} -> Response: ${response.status} ${response.body}" }
        } else {
            logger.info { "Mock $mockId: Request: ${request.method} ${request.path} -> Response: null" }
        }
    }
}