package com.coursemsc.product.shared.repository

import com.coursemsc.product.products.model.Category
import com.coursemsc.product.products.model.Product
import com.coursemsc.product.products.model.Supplier
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Int> {
    @Query("select a from category as a where a.description like %:description%")
    fun findByDescriptionIgnoreCaseContaining(description: String): List<Category>
}

@Repository
interface ProductRepository : JpaRepository<Product, Int>

@Repository
interface SupplierRepository : JpaRepository<Supplier, Int>