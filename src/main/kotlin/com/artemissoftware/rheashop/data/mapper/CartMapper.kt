package com.artemissoftware.rheashop.data.mapper

import com.artemissoftware.rheashop.data.database.entities.CartEntity
import com.artemissoftware.rheashop.data.database.entities.CartItemEntity
import com.artemissoftware.rheashop.data.network.dto.CartDto
import com.artemissoftware.rheashop.data.network.dto.CartItemDto

fun CartEntity.toDto(): CartDto{
    return CartDto(
        id = id,
        items = items.map { it.toDto() },
        totalAmount = totalAmount
    )
}

fun CartItemEntity.toDto(): CartItemDto{
    return CartItemDto(
        id = id,
        quantity = quantity,
        unitPrice = unitPrice,
        product = product!!.toDto()
    )
}