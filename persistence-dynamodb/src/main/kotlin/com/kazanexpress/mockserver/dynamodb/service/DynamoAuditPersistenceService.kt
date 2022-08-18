package com.kazanexpress.mockserver.dynamodb.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.kazanexpress.mockserver.audit.model.RequestLog
import com.kazanexpress.mockserver.audit.service.AuditPersistence
import com.kazanexpress.mockserver.model.Request
import com.kazanexpress.mockserver.model.Response
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.*
import java.time.LocalDateTime

@Service
class DynamoAuditPersistenceService(
    private val dynamoDbClient: DynamoDbClient,
) : AuditPersistence {

    override fun save(request: RequestLog): Mono<RequestLog> {
        dynamoDbClient.batchWriteItem(
            BatchWriteItemRequest.builder()
                .requestItems(
                    mapOf(
                        "RequestLog" to listOf(
                            WriteRequest.builder()
                                .putRequest(
                                    PutRequest.builder()
                                        .item(
                                            request.toPropertyMap()
                                        )
                                        .build()
                                )
                                .build()
                        )
                    )
                )
                .build()
        )
        return Mono.just(request)
    }

    override fun getRequestLogs(
        mockId: String,
        from: LocalDateTime,
        to: LocalDateTime,
        page: Int,
        size: Int
    ): Flux<RequestLog> {
        return Flux.fromIterable(
            dynamoDbClient.query(
                QueryRequest.builder()
                    .tableName("RequestLog")
                    .keyConditionExpression("mockId = :mockId")
                    .conditionalOperator(ConditionalOperator.AND)
                    .filterExpression("dateTime between :from and :to")
                    .expressionAttributeValues(
                        mapOf(
                            ":from" to AttributeValue.builder().s(from.toString()).build(),
                            ":to" to AttributeValue.builder().s(to.toString()).build(),
                            ":mockId" to AttributeValue.builder().s(mockId).build()
                        )
                    )
                    .attributesToGet(
                        listOf(
                            "id",
                            "mockId",
                            "request",
                            "response",
                            "dateTime"
                        )
                    )
                    .build()
            ).items()
        ).map {
            it.toRequestLog()
        }
    }

    private fun RequestLog.toPropertyMap(): Map<String, AttributeValue> {
        return mapOf(
            "id" to AttributeValue.builder().s(this.id).build(),
            "mockId" to AttributeValue.builder().s(this.mockId).build(),
            "request" to AttributeValue.builder().s(this.request.toJson()).build(),
            "response" to AttributeValue.builder().s(this.response.toJson()).build(),
            "dateTime" to AttributeValue.builder().s(this.dateTime.toString()).build()
        )
    }

    private fun MutableMap<String, AttributeValue>.toRequestLog(): RequestLog {
        return RequestLog(
            this["id"]!!.s(),
            this["request"]!!.s().fromJson(Request::class.java),
            this["response"]?.s().fromJson(Response::class.java),
            LocalDateTime.parse(this["dateTime"]!!.s()),
            this["mockId"]!!.s()
        )
    }
}

private val objectMapper: ObjectMapper = ObjectMapper().registerModule(JavaTimeModule())

fun Any?.toJson(): String? {
    return objectMapper.writeValueAsString(this)
}

fun <T> String?.fromJson(java: Class<T>): T {
    return objectMapper.readValue(this, java)
}
