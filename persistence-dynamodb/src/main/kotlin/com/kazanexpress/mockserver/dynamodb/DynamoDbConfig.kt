package com.kazanexpress.mockserver.dynamodb

import com.kazanexpress.mockserver.dynamodb.property.DynamoDbProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.awscore.defaultsmode.DefaultsMode
import software.amazon.awssdk.core.retry.RetryPolicy
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI
import java.time.Duration


@EnableConfigurationProperties
@ComponentScan
@Configuration
class DynamoDbConfig(private val properties: DynamoDbProperties) {

    @Bean(name = ["amazonDynamoDB"])
    fun dynamoDbClient(): DynamoDbClient {
        return DynamoDbClient.builder()
            .dualstackEnabled(false)
            .defaultsMode(DefaultsMode.LEGACY)
            .overrideConfiguration { builder ->
                builder.retryPolicy(RetryPolicy.builder().numRetries(0).build())
                    .apiCallTimeout(Duration.ofSeconds(2))
                    .apiCallAttemptTimeout(Duration.ofSeconds(2))
            }
            .endpointOverride(URI.create(properties.serverUrl))
            .region(Region.of(properties.region))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(
                        properties.accessKey,
                        properties.accessSecret
                    )
                )
            )
            .build()
    }
}