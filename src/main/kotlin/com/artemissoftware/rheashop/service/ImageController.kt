package com.artemissoftware.rheashop.service

import com.artemissoftware.rheashop.data.network.response.ApiResponse
import com.artemissoftware.rheashop.exception.ResourceNotFoundException
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.awt.Image
import java.sql.SQLException

@RestController
@RequestMapping("\${api.prefix:/api/v1}/images")
class ImageController(private val imageService: ImageService) {

    @PostMapping("/upload")
    fun saveImages(
        @RequestParam files: List<MultipartFile>,
        @RequestParam productId: Long
    ): ResponseEntity<ApiResponse> {
        try {
            val images = imageService.saveImages(productId, files)
            return ResponseEntity.ok(ApiResponse("Upload success!", images))
        } catch (e: Exception) {
            return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ApiResponse("Upload failed!", e.message))
        }
    }

    @GetMapping("/image/download/{imageId}")
    @Throws(SQLException::class)
    fun downloadImage(@PathVariable imageId: Long): ResponseEntity<Resource> {

        val image = imageService.getImageById(imageId)
        image.image?.let { fileImage ->
            val resource = ByteArrayResource(fileImage.getBytes(1, fileImage.length().toInt()))

            return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(image.fileType))
                .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    ("attachment; filename=\"" + image.fileName) + "\""
                )
                .body(resource)
        } ?: run {
            return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ByteArrayResource(ByteArray(0)))
        }
    }

    @PutMapping("/image/{imageId}/update")
    fun updateImage(
        @PathVariable imageId: Long,
        @RequestBody file: MultipartFile
    ): ResponseEntity<ApiResponse> {
        try {
            imageService.updateImage(file, imageId)
            return ResponseEntity
                .ok(ApiResponse("Update success!", null))

        } catch (e: ResourceNotFoundException) {
            return ResponseEntity
                .status(NOT_FOUND)
                .body(ApiResponse("Update failed!", e.message))
        }
    }

    @DeleteMapping("/image/{imageId}/delete")
    fun deleteImage(@PathVariable imageId: Long): ResponseEntity<ApiResponse> {
        try {
            imageService.deleteImageById(imageId)
            return ResponseEntity
                .ok(ApiResponse("Delete success!", null))
        } catch (e: ResourceNotFoundException) {
            return ResponseEntity
                .status(NOT_FOUND)
                .body(ApiResponse("Delete failed!", e.message))
        }
    }
}