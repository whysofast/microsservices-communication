package com.coursemsc.product.config.rabbit

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.Binding.DestinationType.QUEUE
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig(
    @Value("\${app-config.rabbit.exchange.product}") val productTopicExchange: String,
    @Value("\${app-config.rabbit.routing-key.product-stock}") val productStockKey: String,
    @Value("\${app-config.rabbit.routing-key.sales-confirmation}") val salesConfirmationKey: String,
    @Value("\${app-config.rabbit.queue.product-stock}") val productStockMq: String,
    @Value("\${app-config.rabbit.queue.sales-confirmation}") val salesConfirmationMq: String,
) {

    @Bean
    fun productTopicExchange() = TopicExchange(productTopicExchange)

    @Bean
    fun productStockMq() = Queue(productStockMq, true)

    @Bean
    fun salesConfirmationMq() = Queue(salesConfirmationMq, true)

    @Bean
    fun productStockMqBinding(topicExchange: TopicExchange): Binding = BindingBuilder
        .bind(productStockMq())
        .to(topicExchange)
        .with(productStockKey)

    @Bean
    fun salesConfirmationMqBinding(topicExchange: TopicExchange): Binding = BindingBuilder
        .bind(salesConfirmationMq())
        .to(topicExchange)
        .with(salesConfirmationKey)

    @Bean
    fun jsonMessageConverter() : MessageConverter = Jackson2JsonMessageConverter()
}