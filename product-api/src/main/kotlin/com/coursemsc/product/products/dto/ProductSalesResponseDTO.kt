package com.coursemsc.product.products.dto

import com.coursemsc.product.products.model.Product

data class ProductSalesResponseDTO(
    val id: Int,
    val name: String,
    val category: CategoryResponseDTO,
    val supplier: SupplierResponseDTO,
    val quantityAvailable: Long,
    val sales: List<String>
)

fun Product.toSalesResponseDTO(sales: List<String>) = ProductSalesResponseDTO(
    id = id!!,
    name = name,
    category = category.toDto(),
    supplier = supplier.toDto(),
    quantityAvailable = quantityAvailable,
    sales = sales,
)
