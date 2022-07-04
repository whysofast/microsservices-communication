package com.coursemsc.product.products.service

import com.coursemsc.product.products.dto.ProductRequestDTO
import com.coursemsc.product.products.model.Product
import com.coursemsc.product.category.service.CategoryService
import com.coursemsc.product.products.dto.ProductStockDTO
import com.coursemsc.product.service.SupplierService
import com.coursemsc.product.shared.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val repository: ProductRepository,
    private val categoryService: CategoryService,
    private val supplierService: SupplierService,
) {
    fun save(productRequest: ProductRequestDTO) : Product {
        val category = categoryService.findById(productRequest.categoryId) ?: throw RuntimeException("Category not found")
        val supplier = supplierService.findById(productRequest.supplierId) ?: throw RuntimeException("Supplier not found")
        return repository.save(productRequest.toModel(category, supplier))
    }

    fun updateProductStock(productStockDTO: ProductStockDTO) {
        println(productStockDTO)
    }
}