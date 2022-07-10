package com.coursemsc.product.sales.dto

import com.coursemsc.product.products.dto.ProductStockDTO

data class SalesConfirmationDTO(
    val salesId: String,
    val status: SalesStatus
)

enum class SalesStatus {
    APPROVED,
    REJECTED,
}

fun ProductStockDTO.toSalesConfirmation(status: SalesStatus) = SalesConfirmationDTO(
    salesId = salesId,
    status = status
)