package com.coursemsc.product.products.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "category")
data class Category(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int?,
    val description: String
)
