package com.artemissoftware.rheashop.data.mapper

import com.artemissoftware.rheashop.data.database.entities.ImageEntity
import com.artemissoftware.rheashop.data.network.dto.ImageDto

fun ImageEntity.toDto(): ImageDto {
    return ImageDto(
        id = id,
        fileName = fileName,
        downloadUrl = downloadUrl
    )
}