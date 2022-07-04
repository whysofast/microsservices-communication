package com.coursemsc.product.products.dto

data class ProductStockDTO(
    val salesId: String,
    val products: List<ProductQuantityDTO>
)

data class ProductQuantityDTO(
    val productId: Int,
    val quantity: Int
)