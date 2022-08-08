package com.kazanexpress.mockserver.audit.model

import com.kazanexpress.mockserver.model.Request
import com.kazanexpress.mockserver.model.Response
import java.time.LocalDateTime
import java.util.*

class RequestLog(
    val mockId: String,
    val request: Request,
    val response: Response?,
    val dateTime: LocalDateTime = LocalDateTime.now(),
    val id: String = UUID.randomUUID().toString()
)