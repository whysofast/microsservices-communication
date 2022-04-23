package com.coursemsc.product.products.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name="product")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int?,

    val name: String,

    @ManyToOne
    @JoinColumn(name = "fk_supplier")
    val supplier: Supplier,

    @ManyToOne
    @JoinColumn(name = "fk_category")
    val category: Category,

    val quantityAvailable: Int,
)
