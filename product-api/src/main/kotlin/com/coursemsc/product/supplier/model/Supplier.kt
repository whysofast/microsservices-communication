package com.coursemsc.product.products.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "supplier")
data class Supplier(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int?,
    val name: String
)
