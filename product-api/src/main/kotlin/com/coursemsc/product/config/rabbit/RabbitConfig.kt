package com.coursemsc.product.config.rabbit

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitConfig(
    private val rabbitConfig: RabbitValuesConfig
) {

    @Bean
    fun productTopicExchange() = TopicExchange(rabbitConfig.productTopicExchange)

    @Bean
    fun productStockMq() = Queue(rabbitConfig.productStockMq, true)

    @Bean
    fun salesConfirmationMq() = Queue(rabbitConfig.salesConfirmationMq, true)

    @Bean
    fun productStockMqBinding(topicExchange: TopicExchange): Binding = BindingBuilder
        .bind(productStockMq())
        .to(topicExchange)
        .with(rabbitConfig.productStockKey)

    @Bean
    fun salesConfirmationMqBinding(topicExchange: TopicExchange): Binding = BindingBuilder
        .bind(salesConfirmationMq())
        .to(topicExchange)
        .with(rabbitConfig.salesConfirmationKey)

    @Bean
    fun jsonMessageConverter(): MessageConverter = Jackson2JsonMessageConverter()
}