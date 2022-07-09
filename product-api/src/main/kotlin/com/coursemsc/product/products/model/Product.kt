package com.coursemsc.product.products.model

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "product")
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

    var quantityAvailable: Long,

    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime? = LocalDateTime.now()
) {
    // mover pra um model depois, separa em Product e ProductDBO
    fun updateStock(quantity: Long) = this.apply { quantityAvailable -= quantity }
}
