package com.coursemsc.product.shared.repository

import com.coursemsc.product.products.model.Category
import com.coursemsc.product.products.model.Product
import com.coursemsc.product.products.model.Supplier
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Int>

@Repository
interface ProductRepository : JpaRepository<Product, Int>

@Repository
interface SupplierRepository : JpaRepository<Supplier, Int>