package com.artemissoftware.rheashop.repository

import com.artemissoftware.rheashop.data.database.entities.ImageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository : JpaRepository<ImageEntity, Long> {
    fun findByProductId(id: Long?): List<ImageEntity>
}