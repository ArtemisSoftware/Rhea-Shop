package com.artemissoftware.rheashop.data.network.dto

import java.math.BigDecimal

data class OrderItemDto(
    val id: Long,
    val name: String,
    val brand: String,
    val quantity: Int,
    val price: BigDecimal
)

