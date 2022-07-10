package com.coursemsc.product.sales.rabbitmq

import com.coursemsc.product.config.rabbit.RabbitValuesConfig
import com.coursemsc.product.sales.dto.SalesConfirmationDTO
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class SalesConfirmationSender(
    private val rabbitTemplate: RabbitTemplate,
    private val rabbitConfig: RabbitValuesConfig,
) {
    fun sendMessage(message: SalesConfirmationDTO) {
        runCatching {
            println("Sending message $message")
            rabbitTemplate.convertAndSend(
                rabbitConfig.productTopicExchange,
                rabbitConfig.salesConfirmationKey,
                message
            )
            println("Message sent successfully")
        }.onFailure  {
            println("Error when trying to send message: $it")
        }
    }
}