package com.kazanexpress.api.mockserver.service.matcher

import com.kazanexpress.api.mockserver.model.Request

interface RequestMatcher {

    fun match(request: Request, mockRequest: Request): Double
}
