package com.kazanexpress.mockserver.dynamodb.service

import com.kazanexpress.mockserver.model.MockData
import com.kazanexpress.mockserver.service.MockDataPersistence
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest
import java.time.LocalDateTime

@Service
class DynamoMockDataPersistence(
    private val dynamoDbClient: DynamoDbClient
) : MockDataPersistence {

    override fun save(mockData: MockData): Mono<MockData> {
        try {
            dynamoDbClient.updateItem(
                UpdateItemRequest.builder()
                    .tableName("MockData")
                    .key(mapOf("mockId" to AttributeValue.builder().s(mockData.id).build()))
                    .attributeUpdates(
                        mapOf(
                            "data" to AttributeValueUpdate.builder().value(AttributeValue.fromS(mockData.toJson()))
                                .build(),
                            "dateTime" to AttributeValueUpdate.builder()
                                .value(AttributeValue.fromS(LocalDateTime.now().toString())).build(),
                        )
                    )
                    .build()
            )
        } catch (e: Exception) {
            return Mono.error(e)
        }
        return Mono.just(mockData)
    }

    override fun get(id: String): Mono<MockData> {
        val itemResponse = dynamoDbClient.getItem(
            GetItemRequest.builder()
                .tableName("MockData")
                .key(mapOf("mockId" to AttributeValue.builder().s(id).build()))
                .projectionExpression("data, createdDate")
                .build()
        )
        if (!itemResponse.hasItem()) {
            return Mono.empty()
        }
        return Mono.just(
            itemResponse.item().toMockData()
        )
    }
}

private fun Map<String, AttributeValue>.toMockData(): MockData {
    return this["data"]!!.s().fromJson(MockData::class.java)
}
