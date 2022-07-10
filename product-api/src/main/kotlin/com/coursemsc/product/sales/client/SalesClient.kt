package com.coursemsc.product.sales.client

import com.coursemsc.product.products.dto.ProductSalesResponseDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(
    name = "salesClient",
    contextId = "salesClient",
    url = "\${app-config.services.sales}"
)
interface SalesClient {

    @GetMapping("/products/{productId}")
    fun findSalesByProductId(@PathVariable productId: Int): ProductSalesResponseDTO?
}