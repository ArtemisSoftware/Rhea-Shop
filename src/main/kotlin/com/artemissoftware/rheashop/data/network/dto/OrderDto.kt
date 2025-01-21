package com.artemissoftware.rheashop.data.network.dto

import java.math.BigDecimal
import java.time.LocalDate

data class OrderDto (
    val id: Long,
    val userId: Long,
    val orderDate: LocalDate,
    val totalAmount: BigDecimal,
    val status: String,
    val items: List<OrderItemDto>
)