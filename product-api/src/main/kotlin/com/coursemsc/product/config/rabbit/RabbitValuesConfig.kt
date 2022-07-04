package com.coursemsc.product.config.rabbit

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class RabbitValuesConfig(
    @Value("\${app-config.rabbit.exchange.product}") val productTopicExchange: String,
    @Value("\${app-config.rabbit.routing-key.product-stock}") val productStockKey: String,
    @Value("\${app-config.rabbit.routing-key.sales-confirmation}") val salesConfirmationKey: String,
    @Value("\${app-config.rabbit.queue.product-stock}") val productStockMq: String,
    @Value("\${app-config.rabbit.queue.sales-confirmation}") val salesConfirmationMq: String,
)