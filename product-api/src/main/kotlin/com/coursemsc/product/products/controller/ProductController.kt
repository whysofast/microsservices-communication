package com.coursemsc.product.products.controller

import com.coursemsc.product.products.dto.ProductRequestDTO
import com.coursemsc.product.products.dto.ProductResponseDTO
import com.coursemsc.product.products.dto.toDto
import com.coursemsc.product.products.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@RestController
@RequestMapping("/api/product")
class ProductController(
    private val service: ProductService
) {

    @PostMapping
    fun save(
        @Valid @RequestBody request: ProductRequestDTO
    ): ResponseEntity<ProductResponseDTO> {

        val response = service.save(request).toDto()
        return status(HttpStatus.CREATED).body(response)
    }
}