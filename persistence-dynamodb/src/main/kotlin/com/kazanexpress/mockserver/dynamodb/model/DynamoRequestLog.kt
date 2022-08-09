package com.kazanexpress.mockserver.dynamodb.model

import com.amazonaws.services.dynamodbv2.datamodeling.*
import com.kazanexpress.mockserver.model.Request
import com.kazanexpress.mockserver.model.Response
import org.springframework.data.annotation.Id
import java.sql.Timestamp

@DynamoDBTable(tableName = "RequestLog")
class DynamoRequestLog(
    @Id
    @DynamoDBHashKey
    val id: String,
    @DynamoDBAttribute
    val mockId: String,
    @DynamoDBTypeConvertedJson
    val request: Request,
    @DynamoDBTypeConvertedJson
    val response: Response?,
    @DynamoDBTypeConvertedTimestamp
    @DynamoDBAttribute
    val dateTime: Timestamp
) {
}
