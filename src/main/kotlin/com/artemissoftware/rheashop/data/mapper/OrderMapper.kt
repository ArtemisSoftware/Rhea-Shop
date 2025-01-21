package com.artemissoftware.rheashop.data.mapper

import com.artemissoftware.rheashop.data.database.entities.OrderEntity
import com.artemissoftware.rheashop.data.database.entities.OrderItemEntity
import com.artemissoftware.rheashop.data.network.dto.OrderDto
import com.artemissoftware.rheashop.data.network.dto.OrderItemDto

fun OrderEntity.toDto(): OrderDto{
    return OrderDto(
        id = id,
        userId = user!!.id,
        orderDate = orderDate,
        totalAmount = totalAmount,
        status = status.toString(),
        items = items.map { it.toDto() }
    )
}

fun OrderItemEntity.toDto(): OrderItemDto{
    return OrderItemDto(
        id = id,
        name = product?.name ?: "",
        brand = product?.brand ?: "",
        quantity = quantity,
        price = price
    )
}