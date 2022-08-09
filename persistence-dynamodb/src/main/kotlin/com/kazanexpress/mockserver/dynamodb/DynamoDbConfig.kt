package com.kazanexpress.mockserver.dynamodb

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration


@EnableDynamoDBRepositories(basePackages = ["com.kazanexpress.mockserver.dynamodb.dao"])
@ComponentScan
@Configuration
class DynamoDbConfig {

    @Value("\${amazon.dynamodb.endpoint}")
    private var serviceUrl = ""

    @Value("\${amazon.dynamodb.region}")
    private var region = ""

    @Value("\${amazon.dynamodb.access-key}")
    private var accessKey = ""

    @Value("\${amazon.dynamodb.secret-key}")
    private var accessSecret = ""

    @Bean(name = ["amazonDynamoDB"])
    fun amazonDynamoDb(): AmazonDynamoDB {
        return AmazonDynamoDBAsyncClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(accessKey, accessSecret)))
            .withEndpointConfiguration(
                AwsClientBuilder.EndpointConfiguration(serviceUrl, region)
            )
            .build()
    }
}