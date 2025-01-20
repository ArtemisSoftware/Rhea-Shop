package com.artemissoftware.rheashop.data.network.dto

import java.math.BigDecimal

data class CartDto(
    val cartId: Long,
    val items: Set<CartItemDto>,
    val totalAmount: BigDecimal
)

