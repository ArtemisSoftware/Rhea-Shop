package com.artemissoftware.rheashop.repository

import com.artemissoftware.rheashop.data.database.entities.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<ProductEntity, Long> {
    fun findByCategoryName(category: String): List<ProductEntity>
    fun findByBrand(brand: String): List<ProductEntity>
    fun findByCategoryNameAndBrand(category: String, brand: String): List<ProductEntity>
    fun findByName(name: String): List<ProductEntity>
    fun findByBrandAndName(brand: String, name: String): List<ProductEntity>
    fun countByBrandAndName(brand: String, name: String): Long
    fun existsByNameAndBrand(name: String, brand: String): Boolean
}