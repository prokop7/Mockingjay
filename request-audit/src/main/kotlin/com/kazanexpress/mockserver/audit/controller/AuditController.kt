package com.kazanexpress.mockserver.audit.controller

import com.kazanexpress.mockserver.audit.model.RequestLog
import com.kazanexpress.mockserver.audit.service.AuditService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import java.time.LocalDateTime

@RequestMapping("/audit")
@RestController
class AuditController(
    private val auditService: AuditService
) {
    @GetMapping("/{mockId}")
    fun logs(
        @PathVariable mockId: String,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy.dd.MM HH:mm") from: LocalDateTime?,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy.dd.MM HH:mm") to: LocalDateTime?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Flux<RequestLog> {
        return auditService.getLogs(mockId, from ?: defaultFrom(), to ?: defaultTo(), page, size)
    }

    private fun defaultFrom() = LocalDateTime.now().minusMonths(1)

    private fun defaultTo() = LocalDateTime.now()
}