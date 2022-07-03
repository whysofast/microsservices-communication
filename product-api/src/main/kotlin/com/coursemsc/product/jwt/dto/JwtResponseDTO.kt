package com.coursemsc.product.jwt.dto

import io.jsonwebtoken.Claims

data class JwtResponseDTO(
    val id: Int,
    val name: String,
    val email: String,
)

fun Claims.toJwtResponseDTO() = JwtResponseDTO(
    id = this["id"] as Int,
    name = this["name"] as String,
    email = this["email"] as String
)