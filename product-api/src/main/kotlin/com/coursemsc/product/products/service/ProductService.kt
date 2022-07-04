package com.coursemsc.product.products.service

import com.coursemsc.product.products.dto.ProductRequestDTO
import com.coursemsc.product.products.model.Product
import com.coursemsc.product.category.service.CategoryService
import com.coursemsc.product.config.exception.ValidationException
import com.coursemsc.product.products.dto.ProductStockDTO
import com.coursemsc.product.sales.SalesStatus
import com.coursemsc.product.sales.SalesStatus.REJECTED
import com.coursemsc.product.sales.rabbitmq.SalesConfirmationSender
import com.coursemsc.product.sales.toSalesConfirmation
import com.coursemsc.product.service.SupplierService
import com.coursemsc.product.shared.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val repository: ProductRepository,
    private val categoryService: CategoryService,
    private val supplierService: SupplierService,
    private val salesConfirmation: SalesConfirmationSender
) {
    fun save(productRequest: ProductRequestDTO) : Product {
        val category = categoryService.findById(productRequest.categoryId) ?: throw RuntimeException("Category not found")
        val supplier = supplierService.findById(productRequest.supplierId) ?: throw RuntimeException("Supplier not found")
        return repository.save(productRequest.toModel(category, supplier))
    }

    fun updateProductStock(productStockDTO: ProductStockDTO) {
        try {
            productStockDTO.validateStockUpdateData()
        } catch (e: Exception) {
            println("Error while trying to update stock : ${e.message}")
            salesConfirmation.sendMessage(productStockDTO.toSalesConfirmation(REJECTED))
        }
    }

    fun ProductStockDTO.validateStockUpdateData() {
        if (salesId.isEmpty()) throw ValidationException("SalesId must be informed")
        if (products.isEmpty()) throw ValidationException("Sales products must be informed")
        if (products.any { it.productId.toString().isEmpty() || it.quantity.toString().isEmpty() })
            throw ValidationException("Product data not concise")
    }
}