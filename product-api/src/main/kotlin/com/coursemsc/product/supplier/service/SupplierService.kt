package com.coursemsc.product.service

import com.coursemsc.product.products.model.Supplier
import com.coursemsc.product.shared.repository.SupplierRepository
import org.springframework.stereotype.Service

@Service
class SupplierService(
    private val repository: SupplierRepository
) {
    fun save(supplier: Supplier) = repository.save(supplier)

    fun findById(id: Int) = repository.findById(id).orElseGet { null }
}