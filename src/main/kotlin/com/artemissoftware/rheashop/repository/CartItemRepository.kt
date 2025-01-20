package com.artemissoftware.rheashop.repository

import com.artemissoftware.rheashop.data.database.entities.CartItemEntity
import org.springframework.data.jpa.repository.JpaRepository


interface CartItemRepository : JpaRepository<CartItemEntity, Long> {
    fun deleteAllByCartId(id: Long)
}