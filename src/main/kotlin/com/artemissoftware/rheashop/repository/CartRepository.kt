package com.artemissoftware.rheashop.repository

import com.artemissoftware.rheashop.data.database.entities.CartEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : JpaRepository<CartEntity, Long> {
    fun findByUserId(userId: Long): CartEntity
}