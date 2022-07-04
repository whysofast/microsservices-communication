package com.coursemsc.product.rabbitmq

import com.coursemsc.product.products.dto.ProductStockDTO
import com.coursemsc.product.products.service.ProductService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class ProductStockListener(
    private val productService: ProductService,
) {

//    @RabbitListener(queues = ["\${app-config.rabbit.routing-key.product-stock}"])
    @RabbitListener(queues = ["product-stock-update.routingKey"])
    fun updateProductStock(productStockDto: ProductStockDTO) {
        productService.updateProductStock(productStockDto)
    }
}