package com.artemissoftware.rheashop.data.network.dto

import java.math.BigDecimal

data class CartDto(
    val id: Long,
    val items: List<CartItemDto>,
    val totalAmount: BigDecimal
)

