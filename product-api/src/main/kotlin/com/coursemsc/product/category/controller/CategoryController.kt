package com.coursemsc.product.category.controller

import com.coursemsc.product.products.dto.CategoryRequestDTO
import com.coursemsc.product.products.dto.CategoryResponseDTO
import com.coursemsc.product.products.dto.toDto
import com.coursemsc.product.category.service.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/category")
class CategoryController(
    private val service: CategoryService
) {

    @PostMapping
    fun save(
        @Valid @RequestBody request: CategoryRequestDTO
    ): ResponseEntity<CategoryResponseDTO> {

        val response = service.save(request.toModel()).toDto()
        return status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    fun findAll(): List<CategoryResponseDTO> = service.findAll().map { it.toDto() }

    @GetMapping("/description/{desc}")
    fun findByDescription(
        @PathVariable(value = "desc") description: String
    ): List<CategoryResponseDTO> = service.findByDescription(description).map { it.toDto() }
}