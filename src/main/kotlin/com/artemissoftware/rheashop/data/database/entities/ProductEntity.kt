package com.artemissoftware.rheashop.data.database.entities

import jakarta.persistence.*
import java.math.BigDecimal


class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var name: String = ""
    var brand: String = ""
    var price: BigDecimal = BigDecimal(0)
    var inventory = 0
    var description: String = ""

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "category_id")
    var category: CategoryEntity? = null

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], orphanRemoval = true)
    var images: List<ImageEntity> = emptyList()
}