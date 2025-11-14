package com.example.bodega.data.repository

import com.example.bodega.data.dao.CategoryDao
import com.example.bodega.data.entity.Category
import com.example.bodega.data.entity.relations.CategoryWithProducts
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val categoryDao: CategoryDao) {
    
    // CREATE
    suspend fun insert(category: Category): Long = categoryDao.insert(category)
    suspend fun insertAll(categories: List<Category>) = categoryDao.insertAll(categories)
    
    // READ
    fun getAllCategories(): Flow<List<Category>> = categoryDao.getAllCategories()
    suspend fun getCategoryById(id: Int): Category? = categoryDao.getCategoryById(id)
    fun searchCategories(query: String): Flow<List<Category>> = categoryDao.searchCategories(query)
    
    // Relaciones 1-n
    suspend fun getCategoryWithProducts(categoryId: Int): CategoryWithProducts? = 
        categoryDao.getCategoryWithProducts(categoryId)
    fun getAllCategoriesWithProducts(): Flow<List<CategoryWithProducts>> = 
        categoryDao.getAllCategoriesWithProducts()
    
    // UPDATE
    suspend fun update(category: Category) = categoryDao.update(category)
    
    // DELETE
    suspend fun delete(category: Category) = categoryDao.delete(category)
    suspend fun deleteById(id: Int) = categoryDao.deleteById(id)
    suspend fun deleteAll() = categoryDao.deleteAll()
}
