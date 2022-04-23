package com.coursemsc.product.service

import com.coursemsc.product.products.model.Category
import com.coursemsc.product.shared.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val repository: CategoryRepository
) {
    fun save(category: Category) = repository.save(category)
}