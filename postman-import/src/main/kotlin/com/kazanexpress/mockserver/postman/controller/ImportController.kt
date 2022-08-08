package com.kazanexpress.mockserver.postman.controller

import com.kazanexpress.mockserver.postman.model.ImportRs
import com.kazanexpress.mockserver.postman.model.PostmanCollection
import com.kazanexpress.mockserver.postman.service.PostmanImportService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/import/postman")
class ImportController(
    private val importService: PostmanImportService
) {

    init {
        log.info("ImportController created")
    }

    @PostMapping
    fun importApi(@RequestBody postmanCollection: PostmanCollection): Mono<ImportRs> {
        log.info("Importing API: {}", postmanCollection)
        return importService.importPostmanCollection(postmanCollection)
    }

    private companion object {
        val log = LoggerFactory.getLogger(ImportController::class.java)!!
    }
}