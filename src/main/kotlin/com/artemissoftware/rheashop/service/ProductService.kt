package com.artemissoftware.rheashop.service

import com.artemissoftware.rheashop.exception.ProductNotFoundException
import com.artemissoftware.rheashop.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    private fun productExists(name: String, brand: String) = productRepository.existsByNameAndBrand(name, brand)

    fun deleteProductById(id: Long) = with(productRepository) {
        findById(id)
            .ifPresentOrElse(
                { entity -> delete(entity) },
                { throw ProductNotFoundException("Product not found!") }
            )
    }

    fun getAllProducts() = productRepository.findAll()

    fun getProductsByCategory(category: String) = productRepository.findByCategoryName(category)

    fun getProductsByBrand(brand: String) = productRepository.findByBrand(brand)

    fun getProductsByCategoryAndBrand(category: String, brand: String) = productRepository.findByCategoryNameAndBrand(category, brand)

    fun getProductsByName(name: String) = productRepository.findByName(name)

    fun getProductsByBrandAndName(brand: String, name: String) = productRepository.findByBrandAndName(brand, name)

    fun countProductsByBrandAndName(brand: String, name: String) =  productRepository.countByBrandAndName(brand, name)

//    fun getConvertedProducts(products: List<Product?>): List<ProductDto> {
//        return products.stream().map<Any>(this::convertToDto).toList()
//    }
}