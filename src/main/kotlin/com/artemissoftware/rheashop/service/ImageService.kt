package com.artemissoftware.rheashop.service

import com.artemissoftware.rheashop.data.database.entities.ImageEntity
import com.artemissoftware.rheashop.data.mapper.toDto
import com.artemissoftware.rheashop.data.network.dto.ImageDto
import com.artemissoftware.rheashop.exception.ResourceNotFoundException
import com.artemissoftware.rheashop.repository.ImageRepository
import com.artemissoftware.rheashop.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.sql.SQLException
import javax.sql.rowset.serial.SerialBlob


@Service
class ImageService(
    private val imageRepository: ImageRepository,
    private val productRepository: ProductRepository
) {
    fun getImageById(id: Long): ImageEntity {
        return imageRepository.findById(id)
            .orElseThrow<RuntimeException> { ResourceNotFoundException("No image found with id: $id") }
    }

    fun deleteImageById(id: Long) {
        imageRepository.findById(id).ifPresentOrElse(
            { entity -> imageRepository.delete(entity) },
            { throw ResourceNotFoundException("No image found with id: $id") }
        )
    }

    fun saveImages(productId: Long, files: List<MultipartFile>): List<ImageDto> {
        val product = productRepository.findById(productId)
            .orElseThrow { ResourceNotFoundException("Product not found!") }

        val savedImageDtos = mutableListOf<ImageDto>()

        files.forEach { file ->
            try {
                val image = ImageEntity().apply {
                    fileName = file.originalFilename ?: FILE_NAME
                    fileType = file.contentType ?: FILE_TYPE
                    image = SerialBlob(file.bytes)
                    this.product = product
                }

                // Save image to generate ID before setting download URL
                val savedImage = imageRepository.save(image)
                savedImage.downloadUrl = "/api/v1/images/image/download/${savedImage.id}"

                // Save again to persist download URL
                imageRepository.save(savedImage)

                savedImageDtos.add(savedImage.toDto())
            } catch (e: IOException) {
                throw RuntimeException("Error saving image: ${e.message}", e)
            } catch (e: SQLException) {
                throw RuntimeException("Database error: ${e.message}", e)
            }
        }

        return savedImageDtos
    }


    fun updateImage(file: MultipartFile, imageId: Long) {
        val image = getImageById(imageId)
        try {
            image.fileName = file.originalFilename ?: FILE_NAME
            image.fileType = file.contentType ?: FILE_TYPE
            image.image = SerialBlob(file.bytes)
            imageRepository.save(image)
        } catch (e: IOException) {
            throw RuntimeException(e.message)
        } catch (e: SQLException) {
            throw RuntimeException(e.message)
        }
    }

    private companion object {
        const val FILE_NAME = "default_name"
        const val FILE_TYPE = "application/octet-stream"
    }
}