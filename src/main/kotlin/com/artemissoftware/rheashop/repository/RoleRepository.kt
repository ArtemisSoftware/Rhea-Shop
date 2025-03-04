package com.artemissoftware.rheashop.repository

import com.artemissoftware.rheashop.data.database.entities.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<RoleEntity, Long> {
    fun findByName(role: String): RoleEntity?
}