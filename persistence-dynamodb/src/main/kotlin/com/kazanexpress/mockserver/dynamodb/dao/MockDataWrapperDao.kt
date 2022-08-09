package com.kazanexpress.mockserver.dynamodb.dao

import com.kazanexpress.mockserver.dynamodb.model.DynamoMockDataWrapper
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.stereotype.Repository

@EnableScan
@Repository
interface MockDataWrapperDao : DynamoDBCrudRepository<DynamoMockDataWrapper, String> {
}