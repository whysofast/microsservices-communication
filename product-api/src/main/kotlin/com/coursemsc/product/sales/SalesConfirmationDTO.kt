package com.coursemsc.product.sales

import com.coursemsc.product.products.dto.ProductStockDTO
import com.coursemsc.product.sales.SalesStatus.REJECTED

data class SalesConfirmationDTO(
    val salesId: String,
    val status: SalesStatus
)

enum class SalesStatus {
    APPROVED,
    REJECTED,
}

fun ProductStockDTO.toSalesConfirmation(status: SalesStatus)= SalesConfirmationDTO(
    salesId = salesId,
    status = status
)