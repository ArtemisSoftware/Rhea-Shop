package com.artemissoftware.rheashop.data.network.dto

import com.artemissoftware.rheashop.data.database.entities.CategoryEntity
import java.math.BigDecimal

data class ProductDto (
    val id: Long,
    val name: String,
    val brand: String,
    val price: BigDecimal,
    val inventory: Int,
    val description: String,
    val category: CategoryEntity,
    val images: List<ImageDto>
)
