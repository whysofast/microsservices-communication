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
    var quantity: Long
)

//{
//    "salesId" : "aiseuhaseiuhaseiuh123123",
//    "products" : [
//        {
//            "productId": 1000,
//            "quantity" : 4
//        },
//        {
//            "productId": 2000,
//            "quantity" : 1
//        },
//        {
//            "productId": 1000,
//            "quantity" : 10
//        }
//    ]
//}