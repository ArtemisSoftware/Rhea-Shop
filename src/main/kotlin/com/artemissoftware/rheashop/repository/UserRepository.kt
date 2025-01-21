package com.artemissoftware.rheashop.repository

import com.artemissoftware.rheashop.data.database.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): UserEntity?
}