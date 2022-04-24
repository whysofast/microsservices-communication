package com.coursemsc.product.products.dto

import com.coursemsc.product.products.model.Category
import com.coursemsc.product.products.model.Product
import com.coursemsc.product.products.model.Supplier

data class ProductRequestDTO(
    val name: String,
    val categoryId: Int,
    val supplierId: Int,
    val quantityAvailable: Int

) {
    fun toModel(category: Category, supplier: Supplier) = Product(
        id = null,
        name = name,
        category = category,
        supplier = supplier,
        quantityAvailable = quantityAvailable
    )
}

data class ProductResponseDTO(
    val id: Int,
    val name: String,
    val category: CategoryResponseDTO,
    val supplier: SupplierResponseDTO,
    val quantityAvailable: Int
)

fun Product.toDto() = ProductResponseDTO(
    id = id!!,
    name = name,
    category = category.toDto(),
    supplier = supplier.toDto(),
    quantityAvailable = quantityAvailable
)
