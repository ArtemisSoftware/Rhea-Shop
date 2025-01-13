package com.artemissoftware.rheashop.controller

import com.artemissoftware.rheashop.data.mapper.toDto
import com.artemissoftware.rheashop.data.network.request.AddProductRequest
import com.artemissoftware.rheashop.data.network.request.UpdateProductRequest
import com.artemissoftware.rheashop.data.network.response.ApiResponse
import com.artemissoftware.rheashop.exception.AlreadyExistsException
import com.artemissoftware.rheashop.exception.ResourceNotFoundException
import com.artemissoftware.rheashop.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.CONFLICT

@RestController
@RequestMapping("\${api.prefix}/products")
class ProductController(private val productService: ProductService) {

    @GetMapping("/all")
    fun getAllProducts(): ResponseEntity<ApiResponse> {
        val products = productService.getAllProducts()
        return ResponseEntity
            .ok(ApiResponse("success", products))
    }

    @GetMapping("product/{productId}/product")
    fun getProductById(@PathVariable productId: Long): ResponseEntity<ApiResponse> {
        try {
            val product = productService.getProductById(productId)
            return ResponseEntity
                .ok(ApiResponse("success", product))
        } catch (e: ResourceNotFoundException) {
            return ResponseEntity
                .status(NOT_FOUND)
                .body(ApiResponse("Product not found with id $productId", e.message))
        }
    }
/*
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    fun addProduct(@RequestBody product: AddProductRequest): ResponseEntity<ApiResponse> {
        try {
            val product = productService.addProduct(product)
            return ResponseEntity
                .ok(ApiResponse("Add product success!", product.toDto()))
        } catch (e: AlreadyExistsException) {
            return ResponseEntity
                .status(CONFLICT)
                .body(ApiResponse("Error adding product"))
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/product/{productId}/update")
    fun updateProduct(
        @RequestBody request: UpdateProductRequest,
        @PathVariable productId: Long
    ): ResponseEntity<ApiResponse> {
        try {
            val product = productService.updateProduct(request, productId)
            return ResponseEntity
                .ok(ApiResponse("Update product success!", product.toDto()))
        } catch (e: ResourceNotFoundException) {
            return ResponseEntity
                .status(NOT_FOUND)
                .body(ApiResponse("Error updating product with id ${productId}", e.message))
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/product/{productId}/delete")
    fun deleteProduct(@PathVariable productId: Long): ResponseEntity<ApiResponse> {
        try {
            productService.deleteProductById(productId)
            return ResponseEntity
                .ok(ApiResponse("Delete product success!", productId))
        } catch (e: ResourceNotFoundException) {
            return ResponseEntity
                .status(NOT_FOUND)
                .body(ApiResponse("Error deleting product", e.message))
        }
    }
*/
    @GetMapping("/products/by/brand-and-name")
    fun getProductByBrandAndName(
        @RequestParam brandName: String,
        @RequestParam productName: String
    ): ResponseEntity<ApiResponse> {
        try {
            val products = productService.getProductsByBrandAndName(
                brandName,
                productName
            )
            if (products.isEmpty()) {
                return ResponseEntity
                    .status(NOT_FOUND)
                    .body(ApiResponse("No products found ", null))
            }
            return ResponseEntity
                .ok(ApiResponse("success", products))
        } catch (e: Exception) {
            return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body<ApiResponse>(ApiResponse("Error searching product by brand and name", e.message))
        }
    }

    @GetMapping("/products/by/category-and-brand")
    fun getProductByCategoryAndBrand(
        @RequestParam category: String,
        @RequestParam brand: String
    ): ResponseEntity<ApiResponse> {
        try {
            val products = productService.getProductsByCategoryAndBrand(
                category,
                brand
            )
            if (products.isEmpty()) {
                return ResponseEntity
                    .status(NOT_FOUND)
                    .body(ApiResponse("No products found "))
            }

            return ResponseEntity
                .ok(ApiResponse("success", products))
        } catch (e: Exception) {
            return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ApiResponse("Error searching product by category and brand", e.message))
        }
    }

    @GetMapping("/products/{name}/products")
    fun getProductByName(@PathVariable name: String): ResponseEntity<ApiResponse> {
        try {
            val products = productService.getProductsByName(name)
            if (products.isEmpty()) {
                return ResponseEntity
                    .status(NOT_FOUND)
                    .body(ApiResponse("No products found "))
            }

            return ResponseEntity
                .ok(ApiResponse("success", products))
        } catch (e: Exception) {
            return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ApiResponse("Error searching product by name", e.message))
        }
    }

    @GetMapping("/product/by-brand")
    fun findProductByBrand(@RequestParam brand: String): ResponseEntity<ApiResponse> {
        try {
            val products = productService.getProductsByBrand(brand)
            if (products.isEmpty()) {
                return ResponseEntity
                    .status(NOT_FOUND)
                    .body(ApiResponse("No products found "))
            }

            return ResponseEntity
                .ok(ApiResponse("success", products))
        } catch (e: Exception) {
            return ResponseEntity.ok(ApiResponse("Error searching for product by name", e.message))
        }
    }

    @GetMapping("/product/{category}/all/products")
    fun findProductByCategory(@PathVariable category: String): ResponseEntity<ApiResponse> {
        try {
            val products = productService.getProductsByCategory(category)
            if (products.isEmpty()) {
                return ResponseEntity
                    .status(NOT_FOUND)
                    .body(ApiResponse("No products found ", null))
            }

            return ResponseEntity
                .ok(ApiResponse("success", products))
        } catch (e: Exception) {
            return ResponseEntity.ok(ApiResponse("Product not found by category ", e.message))
        }
    }

    @GetMapping("/product/count/by-brand/and-name")
    fun countProductsByBrandAndName(
        @RequestParam brand: String,
        @RequestParam name: String
    ): ResponseEntity<ApiResponse> {
        try {
            val count = productService.countProductsByBrandAndName(brand, name)
            return ResponseEntity
                .ok(ApiResponse("Product count!", count))
        } catch (e: Exception) {
            return ResponseEntity
                .ok(ApiResponse("Count failed", e.message))
        }
    }
}