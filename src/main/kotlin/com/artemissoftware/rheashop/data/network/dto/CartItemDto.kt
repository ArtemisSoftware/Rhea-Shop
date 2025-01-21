package com.artemissoftware.rheashop.data.network.dto

import java.math.BigDecimal

data class CartItemDto (
    val id: Long,
    val quantity: Int,
    val unitPrice: BigDecimal,
    val product: ProductDto
)