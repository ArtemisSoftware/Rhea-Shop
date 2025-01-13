package com.artemissoftware.rheashop.data.mapper

import com.artemissoftware.rheashop.data.database.entities.CategoryEntity
import com.artemissoftware.rheashop.data.database.entities.ProductEntity
import com.artemissoftware.rheashop.data.network.request.AddProductRequest
import com.artemissoftware.rheashop.data.network.request.UpdateProductRequest

fun AddProductRequest.toEntity(category: CategoryEntity): ProductEntity{
    return ProductEntity(
        name = name,
        brand = brand,
        price = price,
        inventory = inventory,
        description = description,
        category = category
    )
}

fun UpdateProductRequest.toEntity(existingProduct: ProductEntity, category: CategoryEntity): ProductEntity {
    existingProduct.name = name
    existingProduct.brand = brand
    existingProduct.price = price
    existingProduct.inventory = inventory
    existingProduct.description = description
    existingProduct.category = category
    return existingProduct
}
