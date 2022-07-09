package com.coursemsc.product.products.service

import com.coursemsc.product.category.service.CategoryService
import com.coursemsc.product.config.exception.ValidationException
import com.coursemsc.product.products.dto.ProductQuantityDTO
import com.coursemsc.product.products.dto.ProductRequestDTO
import com.coursemsc.product.products.dto.ProductStockDTO
import com.coursemsc.product.products.model.Product
import com.coursemsc.product.sales.SalesStatus.APPROVED
import com.coursemsc.product.sales.SalesStatus.REJECTED
import com.coursemsc.product.sales.rabbitmq.SalesConfirmationSender
import com.coursemsc.product.sales.toSalesConfirmation
import com.coursemsc.product.service.SupplierService
import com.coursemsc.product.shared.repository.ProductRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val repository: ProductRepository,
    private val categoryService: CategoryService,
    private val supplierService: SupplierService,
    private val salesConfirmation: SalesConfirmationSender
) {
    fun save(productRequest: ProductRequestDTO): Product {
        val category =
            categoryService.findById(productRequest.categoryId) ?: throw RuntimeException("Category not found")
        val supplier =
            supplierService.findById(productRequest.supplierId) ?: throw RuntimeException("Supplier not found")
        return repository.save(productRequest.toModel(category, supplier))
    }

    fun updateProductStock(productStockDTO: ProductStockDTO) {
        try {
            productStockDTO
                .validateStockUpdateData()
                .agglutinateSimilarProducts()
                .validateStockQuantity()
                .updateStock()

            salesConfirmation.sendMessage(productStockDTO.toSalesConfirmation(APPROVED))
        } catch (e: Exception) {
            println("Error while trying to update stock : ${e.message}")
            salesConfirmation.sendMessage(productStockDTO.toSalesConfirmation(REJECTED))
        }
    }

    fun ProductStockDTO.validateStockUpdateData(): ProductStockDTO {
        if (salesId.isEmpty()) throw ValidationException("SalesId must be informed")
        if (products.isEmpty()) throw ValidationException("Sales products must be informed")
        if (products.any { it.productId.toString().isEmpty() || it.quantity.toString().isEmpty() })
            throw ValidationException("Product data not concise")
        return this
    }

    fun ProductStockDTO.agglutinateSimilarProducts(): ProductStockDTO {
        val distinctProducts = mutableListOf<ProductQuantityDTO>()
        products.forEach { product ->
            if (distinctProducts.none { it.productId == product.productId }) {
                distinctProducts.add(product)
            } else {
                distinctProducts.find { it.productId == product.productId }!!.apply {
                    this.quantity += product.quantity
                }
            }
        }
        return ProductStockDTO(salesId, distinctProducts)
    }

    fun ProductStockDTO.validateStockQuantity(): ProductStockDTO {
        products.forEach {
            val product = repository.findByIdOrNull(it.productId) ?: throw Exception("Product $it not found")
            if (it.quantity > product.quantityAvailable) throw ValidationException("The product $it is out of stock.")
        }
        return this
    }

    @Transactional
    fun ProductStockDTO.updateStock() = products.forEach {
        val product = repository.findByIdOrNull(it.productId) ?: throw Exception("Product $it not found")
        repository.save(product.updateStock(quantity = it.quantity))
    }
}
