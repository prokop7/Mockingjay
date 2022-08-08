package com.kazanexpress.mockserver.audit.service

import com.kazanexpress.mockserver.audit.model.RequestLog
import com.kazanexpress.mockserver.model.Request
import com.kazanexpress.mockserver.model.Response
import com.kazanexpress.mockserver.service.listener.RequestListener
import org.springframework.stereotype.Service

@Service
class AuditRequestListener(
    private val auditPersistence: AuditPersistence
) : RequestListener {

    override fun onComplete(mockId: String, request: Request, response: Response?) {
        val requestLog = RequestLog(mockId, request, response)
        auditPersistence.save(requestLog).subscribe()
    }
}