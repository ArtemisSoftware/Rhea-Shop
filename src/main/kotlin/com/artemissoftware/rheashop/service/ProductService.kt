package com.artemissoftware.rheashop.service

import com.artemissoftware.rheashop.data.database.entities.CategoryEntity
import com.artemissoftware.rheashop.data.database.entities.ProductEntity
import com.artemissoftware.rheashop.data.mapper.toDto
import com.artemissoftware.rheashop.data.mapper.toEntity
import com.artemissoftware.rheashop.data.network.dto.ProductDto
import com.artemissoftware.rheashop.data.network.request.AddProductRequest
import com.artemissoftware.rheashop.data.network.request.UpdateProductRequest
import com.artemissoftware.rheashop.exception.ProductNotFoundException
import com.artemissoftware.rheashop.exception.ResourceNotFoundException
import com.artemissoftware.rheashop.repository.CategoryRepository
import com.artemissoftware.rheashop.repository.ImageRepository
import com.artemissoftware.rheashop.repository.ProductRepository
import org.springframework.stereotype.Service


@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val imageRepository: ImageRepository,
) {

    private fun productExists(name: String, brand: String) = productRepository.existsByNameAndBrand(name, brand)

    fun addProduct(request: AddProductRequest): ProductEntity {
//        if (productExists(request.name, request.brand)) {
//            throw AlreadyExistsException(request.brand + " " + request.name + " already exists, you may update this product instead!")
//        }


        val category = categoryRepository.findByName(request.category.name)
            ?: categoryRepository.save(CategoryEntity(request.category.name))

        request.category = category
        return productRepository.save(request.toEntity(category))
    }

    fun updateProduct(request: UpdateProductRequest, productId: Long): ProductEntity {
        return productRepository.findById(productId)
            .map { existingProduct ->
                categoryRepository.findByName(request.category.name)?.let {
                    request.toEntity(existingProduct = existingProduct, category = it)
                } ?: run {
                    null
                }
            }
            .map { entity -> entity?.let { productRepository.save(it) } }
            .orElseThrow { ProductNotFoundException("Product not found!") }
    }

    fun deleteProductById(id: Long) = with(productRepository) {
        findById(id)
            .ifPresentOrElse(
                { entity -> delete(entity) },
                { throw ProductNotFoundException("Product not found!") }
            )
    }

    fun getAllProducts() = productRepository.findAll().map { convertToDto(it) }

    fun getProductById(id: Long): ProductDto =
        productRepository.findById(id)
            .map { convertToDto(it) }
            .orElseThrow { ResourceNotFoundException("Product not found!") }

    fun getProductsByCategory(category: String) = productRepository.findByCategoryName(category).map { convertToDto(it) }

    fun getProductsByBrand(brand: String) = productRepository.findByBrand(brand).map { convertToDto(it) }

    fun getProductsByCategoryAndBrand(category: String, brand: String) = productRepository.findByCategoryNameAndBrand(category, brand).map { convertToDto(it) }

    fun getProductsByName(name: String) = productRepository.findByName(name).map { convertToDto(it) }

    fun getProductsByBrandAndName(brand: String, name: String) = productRepository.findByBrandAndName(brand, name).map { convertToDto(it) }

    fun countProductsByBrandAndName(brand: String, name: String) =  productRepository.countByBrandAndName(brand, name)

    private fun convertToDto(product: ProductEntity): ProductDto {
        val images = imageRepository.findByProductId(product.id)
        return product.toDto(images)
    }
}