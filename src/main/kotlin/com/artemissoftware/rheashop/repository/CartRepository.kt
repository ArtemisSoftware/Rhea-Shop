package com.artemissoftware.rheashop.repository

import com.artemissoftware.rheashop.data.database.entities.CartEntity
import org.springframework.data.jpa.repository.JpaRepository


interface CartRepository : JpaRepository<CartEntity, Long> {
    fun findByUserId(userId: Long): CartEntity
}