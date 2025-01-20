package com.artemissoftware.rheashop.data.database.entities

import jakarta.persistence.*
import java.math.BigDecimal


@Entity
class OrderItemEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var quantity: Int = 0
    var price: BigDecimal = BigDecimal.ZERO


    @ManyToOne
    @JoinColumn(name = "order_id")
    var order: OrderEntity? = null

    @ManyToOne
    @JoinColumn(name = "product_id")
    var product: ProductEntity? = null

    constructor(
        order: OrderEntity,
        product: ProductEntity,
        quantity: Int,
        price: BigDecimal
    ): this(){
        this.order = order
        this.product = product
        this.quantity = quantity
        this.price = price
    }
}