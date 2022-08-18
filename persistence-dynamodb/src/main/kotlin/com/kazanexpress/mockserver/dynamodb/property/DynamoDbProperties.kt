package com.kazanexpress.mockserver.dynamodb.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank

@Validated
@ConfigurationProperties(prefix = "dynamodb")
@Component
class DynamoDbProperties {
    @NotBlank
    lateinit var serverUrl: String

    @NotBlank
    lateinit var region: String

    @NotBlank
    lateinit var accessKey: String

    @NotBlank
    lateinit var accessSecret: String
}