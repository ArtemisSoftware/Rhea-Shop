package com.artemissoftware.rheashop.data.database.entities

import jakarta.persistence.*
import java.math.BigDecimal


@Entity
class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var totalAmount: BigDecimal = BigDecimal.ZERO

    @OneToMany(mappedBy = "cart", cascade = [CascadeType.ALL], orphanRemoval = true)
    var items: MutableSet<CartItemEntity> = HashSet<CartItemEntity>()

    @OneToOne
    @JoinColumn(name = "user_id")
    var user: UserEntity? = null

    fun addItem(item: CartItemEntity) {
        items.add(item)
        item.cart = this
        updateTotalAmount()
    }

    fun removeItem(item: CartItemEntity) {
        items.remove(item)
        item.cart = null
        updateTotalAmount()
    }

    private fun updateTotalAmount() {
        this.totalAmount = items.stream().map<BigDecimal> { item: CartItemEntity ->
            val unitPrice: BigDecimal = item.unitPrice
            unitPrice.multiply(BigDecimal.valueOf(item.quantity.toDouble()))
        }.reduce(BigDecimal.ZERO) {
            obj: BigDecimal, augend: BigDecimal -> obj.add(augend)
        }
    }

    fun clearCart() {
        items.clear()
        updateTotalAmount()
    }
}