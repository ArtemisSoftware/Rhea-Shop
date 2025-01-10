package com.artemissoftware.rheashop.repository

import com.artemissoftware.rheashop.data.database.entities.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<CategoryEntity, Long> {
    fun findByName(name: String): CategoryEntity?
    fun existsByName(name: String): Boolean
}