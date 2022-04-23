package com.coursemsc.product.supplier.controller

import com.coursemsc.product.products.dto.SupplierRequestDTO
import com.coursemsc.product.products.dto.SupplierResponseDTO
import com.coursemsc.product.products.dto.toDto
import com.coursemsc.product.service.SupplierService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@RestController
@RequestMapping("/api/supplier")
class SupplierController(
    private val service: SupplierService
) {

    @PostMapping
    fun save(
        @Valid @RequestBody request: SupplierRequestDTO
    ): ResponseEntity<SupplierResponseDTO> {

        val response = service.save(request.toModel()).toDto()
        return status(HttpStatus.CREATED).body(response)
    }
}