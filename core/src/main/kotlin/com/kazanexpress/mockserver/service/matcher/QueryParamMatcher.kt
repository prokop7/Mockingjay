package com.kazanexpress.mockserver.service.matcher

import com.kazanexpress.mockserver.model.Request
import com.kazanexpress.mockserver.similarity
import mu.KotlinLogging
import org.springframework.stereotype.Service


@Service
class QueryParamMatcher : RequestMatcher {

    private val logger = KotlinLogging.logger {}

    override fun match(
        request: Request,
        mockRequest: Request
    ): Double {
        val reqParams = request.queryParams
        val mockParams = mockRequest.queryParams
        val intersection = reqParams.keys.intersect(mockParams.keys)
        val res = if (intersection.isEmpty()) {
            if (mockParams.keys.isNotEmpty()) {
                0.0
            } else {
                1.0
            }
        } else {
            val avgSim = intersection.map {
                val reqParam = reqParams[it]
                val mockParam = mockParams[it]
                similarity(reqParam, mockParam)
            }.average()
            avgSim * intersection.size / mockParams.keys.size
        }
        logger.info { "Query params similarity  : $res" }
        return res
    }
}