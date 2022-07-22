package com.coursemsc.product.products.service

import com.coursemsc.product.category.service.CategoryService
import com.coursemsc.product.config.exception.ValidationException
import com.coursemsc.product.products.dto.ProductCheckStockRequestDTO
import com.coursemsc.product.products.dto.ProductQuantityDTO
import com.coursemsc.product.products.dto.ProductRequestDTO
import com.coursemsc.product.products.dto.ProductSalesResponseDTO
import com.coursemsc.product.products.dto.ProductStockDTO
import com.coursemsc.product.products.dto.toSalesResponseDTO
import com.coursemsc.product.products.model.Product
import com.coursemsc.product.sales.client.SalesClient
import com.coursemsc.product.sales.dto.SalesStatus.APPROVED
import com.coursemsc.product.sales.dto.SalesStatus.REJECTED
import com.coursemsc.product.sales.dto.toSalesConfirmation
import com.coursemsc.product.sales.rabbitmq.SalesConfirmationSender
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
    private val salesConfirmation: SalesConfirmationSender,
    private val salesClient: SalesClient
) {
    fun save(productRequest: ProductRequestDTO): Product {
        val category =
            categoryService.findById(productRequest.categoryId) ?: throw RuntimeException("Category not found")
        val supplier =
            supplierService.findById(productRequest.supplierId) ?: throw RuntimeException("Supplier not found")
        return repository.save(productRequest.toModel(category, supplier))
    }

    fun findProductSales(id: Int): ProductSalesResponseDTO {
        repository.findByIdOrNull(id)
            ?.let { product ->
                return salesClient.findSalesByProductId(product.id!!)
                    ?.let { product.toSalesResponseDTO(it.sales) }
                    ?: throw ValidationException("There was an error during find product's sales")
            }
            ?: throw Exception("There is not such product with id: $id")
    }

    fun checkProductsStock(request: ProductCheckStockRequestDTO) {
        request.products.forEach(::validateStock)
    }

    private fun validateStock(productQuantity: ProductQuantityDTO) {
        val product = repository.findByIdOrNull(productQuantity.productId)
            ?: throw Exception("Product $productQuantity not found")

        if (productQuantity.quantity > product.quantityAvailable)
            throw ValidationException("The product $productQuantity is out of stock.")
    }

    fun updateProductStock(productStockDTO: ProductStockDTO) {
        runCatching {
            productStockDTO
                .validateStockUpdateData()
                .joinSimilarProducts()
                .validateStockQuantity()
                .updateStock()

            salesConfirmation.sendMessage(productStockDTO.toSalesConfirmation(APPROVED))
        }.onFailure {
            println("Error while trying to update stock : ${it.message}")
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


    fun ProductStockDTO.joinSimilarProducts(): ProductStockDTO {
        val distinctProducts = products
            .groupBy { it.productId }
            .map { it.toProductQuantityDTO() }
        return ProductStockDTO(salesId, distinctProducts)
    }

    fun Map.Entry<Int, List<ProductQuantityDTO>>.toProductQuantityDTO() =
        ProductQuantityDTO(
            productId = this.key,
            quantity = this.value.sumOf { it.quantity }
        )

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
