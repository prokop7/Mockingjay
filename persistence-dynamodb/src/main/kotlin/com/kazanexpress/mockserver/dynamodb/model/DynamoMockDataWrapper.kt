package com.kazanexpress.mockserver.dynamodb.model

import com.amazonaws.services.dynamodbv2.datamodeling.*
import com.kazanexpress.mockserver.model.MockData
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import java.sql.Timestamp

@DynamoDBTable(tableName = "MockData")
class DynamoMockDataWrapper(
    @DynamoDBAttribute
    @DynamoDBTypeConvertedJson
    val data: MockData,
    @Id
    @DynamoDBHashKey
    val id: String = data.id
) {
    @DynamoDBTypeConvertedTimestamp
    @CreatedDate
    var createdDate: Timestamp? = null
}