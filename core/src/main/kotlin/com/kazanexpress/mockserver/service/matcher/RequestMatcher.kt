package com.kazanexpress.mockserver.service.matcher

import com.kazanexpress.mockserver.model.Request

interface RequestMatcher {

    fun match(request: Request, mockRequest: Request): Double
}
