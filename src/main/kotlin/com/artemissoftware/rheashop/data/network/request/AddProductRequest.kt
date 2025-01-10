package com.artemissoftware.rheashop.data.network.request

import com.artemissoftware.rheashop.data.database.entities.CategoryEntity
import java.math.BigDecimal


class AddProductRequest (
    val id: Long,
    val name: String,
    val brand: String,
    val price: BigDecimal,
    val inventory: Int,
    val description: String,
    var category: CategoryEntity
)
