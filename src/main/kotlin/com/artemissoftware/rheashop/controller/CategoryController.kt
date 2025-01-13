package com.artemissoftware.rheashop.controller

import com.artemissoftware.rheashop.data.database.entities.CategoryEntity
import com.artemissoftware.rheashop.data.network.response.ApiResponse
import com.artemissoftware.rheashop.exception.AlreadyExistsException
import com.artemissoftware.rheashop.exception.ResourceNotFoundException
import com.artemissoftware.rheashop.service.CategoryService
import org.hibernate.grammars.hql.HqlLexer.CONFLICT
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND

@RestController
@RequestMapping("\${api.prefix}/categories")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping("/all")
    fun getAllCategories(): ResponseEntity<ApiResponse> {
        try {
            val categories = categoryService.getAllCategories()
            return ResponseEntity
                .ok(ApiResponse("Found!", categories))
        } catch (e: Exception) {
            return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ApiResponse("Error:", INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/add")
    fun addCategory(@RequestBody name: CategoryEntity): ResponseEntity<ApiResponse> {
        try {
            val category = categoryService.addCategory(name)
            return ResponseEntity
                .ok(ApiResponse("Success", category))
        } catch (e: AlreadyExistsException) {
            return ResponseEntity
                .status(CONFLICT)
                .body(ApiResponse("Error adding category", e.message))
        }
    }

    @GetMapping("/category/{id}/category")
    fun getCategoryById(@PathVariable id: Long): ResponseEntity<ApiResponse> {
        try {
            val category = categoryService.getCategoryById(id)
            return ResponseEntity
                .ok(ApiResponse("Found", category))
        } catch (e: ResourceNotFoundException) {
            return ResponseEntity
                .status(NOT_FOUND)
                .body(ApiResponse("Error getting category by id", e.message))
        }
    }

    @GetMapping("/category/{name}/category")
    fun getCategoryByName(@PathVariable name: String): ResponseEntity<ApiResponse> {
        try {
            val category = categoryService.getCategoryByName(name)
            return ResponseEntity
                .ok(ApiResponse("Found", category))
        } catch (e: ResourceNotFoundException) {
            return ResponseEntity
                .status(NOT_FOUND)
                .body(ApiResponse("Error getting category by name", e.message))
        }
    }

    @DeleteMapping("/category/{id}/delete")
    fun deleteCategory(@PathVariable id: Long): ResponseEntity<ApiResponse> {
        try {
            categoryService.deleteCategoryById(id)
            return ResponseEntity
                .ok(ApiResponse("Found", null))
        } catch (e: ResourceNotFoundException) {
            return ResponseEntity
                .status(NOT_FOUND)
                .body(ApiResponse("Error deleting category", e.message))
        }
    }

    @PutMapping("/category/{id}/update")
    fun updateCategory(
        @PathVariable id: Long,
        @RequestBody category: CategoryEntity
    ): ResponseEntity<ApiResponse> {
        try {
            val updatedCategory = categoryService.updateCategory(category, id)
            return ResponseEntity
                .ok(ApiResponse("Update success!", updatedCategory))
        } catch (e: ResourceNotFoundException) {
            return ResponseEntity
                .status(NOT_FOUND)
                .body(ApiResponse("Error updating category", e.message))
        }
    }
}