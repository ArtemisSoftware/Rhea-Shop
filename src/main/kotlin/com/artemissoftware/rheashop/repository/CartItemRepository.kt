package com.artemissoftware.rheashop.repository

import com.artemissoftware.rheashop.data.database.entities.CartItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartItemRepository : JpaRepository<CartItemEntity, Long> {
    fun deleteAllByCartId(id: Long)
}