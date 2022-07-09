package com.coursemsc.product.sales.rabbitmq

import com.coursemsc.product.config.rabbit.RabbitValuesConfig
import com.coursemsc.product.sales.SalesConfirmationDTO
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class SalesConfirmationSender(
    private val rabbitTemplate: RabbitTemplate,
    private val rabbitConfig: RabbitValuesConfig,
) {
    fun sendMessage(message: SalesConfirmationDTO) {
        try {
            println("Sending message $message")
            rabbitTemplate.convertAndSend(
                rabbitConfig.productTopicExchange,
                rabbitConfig.salesConfirmationKey,
                message
            )
            println("Message sent succesfully")
        } catch (e: Exception) {
            println("Error when trying to send message: $e")
        }
    }
}