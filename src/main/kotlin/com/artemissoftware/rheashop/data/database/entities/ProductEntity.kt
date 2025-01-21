package com.artemissoftware.rheashop.data.database.entities

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
class ProductEntity() {
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

    constructor(
        name: String,
        brand: String,
        price: BigDecimal,
        inventory: Int,
        description: String,
        category: CategoryEntity
    ) : this() {
        this.name = name
        this.brand = brand
        this.price = price
        this.inventory = inventory
        this.description = description
        this.category = category
    }
}