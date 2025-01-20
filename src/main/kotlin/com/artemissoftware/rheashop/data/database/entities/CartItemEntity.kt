package com.artemissoftware.rheashop.data.database.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var quantity: Int = 0
    var unitPrice: BigDecimal = BigDecimal.ZERO
    var totalPrice: BigDecimal = BigDecimal.ZERO

    @ManyToOne
    @JoinColumn(name = "product_id")
    var product: ProductEntity? = null

    @JsonIgnore
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "cart_id")
    var cart: CartEntity? = null

    fun setTotalPrice() {
        this.totalPrice = unitPrice.multiply(BigDecimal(quantity))
    }
}