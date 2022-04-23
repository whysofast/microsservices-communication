package com.coursemsc.product.products.dto

import com.coursemsc.product.products.model.Supplier

data class SupplierRequestDTO(
    val name: String
) {
    fun toModel() = Supplier(
        id = null,
        name = name
    )
}

data class SupplierResponseDTO(
    val id: Int,
    val name: String
)

fun Supplier.toDto() = SupplierResponseDTO(
    id = id!!,
    name = name
)
