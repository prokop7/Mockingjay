package com.kazanexpress.mockserver.service.listener

import com.kazanexpress.mockserver.model.Request
import com.kazanexpress.mockserver.model.Response

interface RequestListener {
    fun onComplete(mockId: String, request: Request, response: Response?)

}
