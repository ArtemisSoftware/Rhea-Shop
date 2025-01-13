package com.artemissoftware.rheashop.service

import com.artemissoftware.rheashop.data.database.entities.CategoryEntity
import com.artemissoftware.rheashop.exception.AlreadyExistsException
import com.artemissoftware.rheashop.exception.ResourceNotFoundException
import com.artemissoftware.rheashop.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    fun getCategoryById(id: Long): CategoryEntity? {
        return categoryRepository.findById(id)
            .orElseThrow<RuntimeException> { ResourceNotFoundException("Category not found!") }
    }

    fun getCategoryByName(name: String): CategoryEntity? {
        return categoryRepository.findByName(name)
    }

    fun getAllCategories(): List<CategoryEntity> {
        return categoryRepository.findAll()
    }

    fun addCategory(category: CategoryEntity): CategoryEntity {
        if(!categoryRepository.existsByName(category.name)) {
            return categoryRepository.save(category)
        }
        else{
            throw AlreadyExistsException(category.name + " already exists")
        }
    }

    fun updateCategory(category: CategoryEntity, id: Long): CategoryEntity {
        return getCategoryById(id)?.let { oldCategory ->
            oldCategory.name = category.name
            categoryRepository.save(oldCategory)
        } ?: run { throw ResourceNotFoundException("Category not found!") }
    }


    fun deleteCategoryById(id: Long) {
        categoryRepository.findById(id)
            .ifPresentOrElse(
                { entity -> categoryRepository.delete(entity) },
                { throw ResourceNotFoundException("Category not found!") }
            )
    }

}