package com.coursemsc.product.products.dto

import com.coursemsc.product.products.model.Category

data class CategoryRequestDTO(
    val description: String
) {
    fun toModel() = Category(
        id = null,
        description = description
    )
}

data class CategoryResponseDTO(
    val id: Int,
    val description: String
)

fun Category.toDto() = CategoryResponseDTO(
    id = id!!,
    description = description
)
