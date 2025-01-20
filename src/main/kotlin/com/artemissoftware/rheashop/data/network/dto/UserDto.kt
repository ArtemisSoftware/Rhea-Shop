package com.artemissoftware.rheashop.data.network.dto

data class UserDto(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val orders: List<OrderDto>,
    val cart: CartDto
)
