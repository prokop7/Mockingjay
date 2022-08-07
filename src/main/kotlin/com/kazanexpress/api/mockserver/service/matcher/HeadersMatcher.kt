package com.kazanexpress.api.mockserver.service.matcher

import com.kazanexpress.api.mockserver.model.Request
import com.kazanexpress.api.mockserver.similarity
import mu.KotlinLogging
import org.springframework.stereotype.Service


@Service
class HeadersMatcher : RequestMatcher {

    private val logger = KotlinLogging.logger {}

    override fun match(
        request: Request,
        mockRequest: Request
    ): Double {
        val reqMap = request.headers
        val mockMap = mockRequest.headers
        val intersection = reqMap.keys.intersect(mockMap.keys)
        val res = if (intersection.isEmpty()) {
            if (mockMap.keys.isNotEmpty()) {
                0.0
            } else {
                1.0
            }
        } else {
            val avgSim = intersection.map {
                val reqParam = reqMap[it]
                val mockParam = mockMap[it]
                similarity(reqParam, mockParam)
            }.average()
            avgSim * intersection.size / mockMap.keys.size
        }
        logger.info { "Headers similarity       : $res" }
        return res
    }
}