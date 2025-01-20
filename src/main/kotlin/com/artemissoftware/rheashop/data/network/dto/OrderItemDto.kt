package com.artemissoftware.rheashop.data.network.dto

import java.math.BigDecimal

data class OrderItemDto(
    val productId: Long,
    val productName: String,
    val productBrand: String,
    val quantity: Int,
    val price: BigDecimal
)

