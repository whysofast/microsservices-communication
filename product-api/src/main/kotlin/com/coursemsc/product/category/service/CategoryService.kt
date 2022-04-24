package com.coursemsc.product.category.service

import com.coursemsc.product.products.model.Category
import com.coursemsc.product.shared.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val repository: CategoryRepository
) {
    fun save(category: Category) = repository.save(category)

    fun findById(id: Int): Category? = repository.findById(id).orElseGet { null }

    fun findByDescription(description: String): List<Category> = repository.findByDescriptionIgnoreCaseContaining(description)

    fun findAll(): List<Category> = repository.findAll()
}