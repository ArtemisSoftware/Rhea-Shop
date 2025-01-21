package com.artemissoftware.rheashop.data.mapper

import com.artemissoftware.rheashop.data.database.entities.UserEntity
import com.artemissoftware.rheashop.data.network.dto.UserDto

fun UserEntity.toDto(): UserDto{
    return UserDto(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        orders = orders.map { it.toDto() },
        cart = cart?.toDto()
    )
}