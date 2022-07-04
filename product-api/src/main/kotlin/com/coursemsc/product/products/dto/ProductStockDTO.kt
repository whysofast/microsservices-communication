package com.coursemsc.product.products.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ProductStockDTO(
    @JsonProperty("salesId")
    val salesId: String,
    @JsonProperty("products")
    val products: List<ProductQuantityDTO>
)
data class ProductQuantityDTO(
    @JsonProperty("productId")
    val productId: Int,
    @JsonProperty("quantity")
    val quantity: Int
)