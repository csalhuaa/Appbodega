package com.example.bodega.data.dao

import androidx.room.*
import com.example.bodega.data.entity.Category
import com.example.bodega.data.entity.relations.CategoryWithProducts
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<Category>)
    
    // READ
    @Query("SELECT * FROM category")
    fun getAllCategories(): Flow<List<Category>>
    
    @Query("SELECT * FROM category WHERE categoryId = :id")
    suspend fun getCategoryById(id: Int): Category?
    
    @Query("SELECT * FROM category WHERE categoryName LIKE '%' || :query || '%'")
    fun searchCategories(query: String): Flow<List<Category>>
    
    // Consulta 1-n: Categoría con sus productos
    @Transaction
    @Query("SELECT * FROM category WHERE categoryId = :categoryId")
    suspend fun getCategoryWithProducts(categoryId: Int): CategoryWithProducts?
    
    @Transaction
    @Query("SELECT * FROM category")
    fun getAllCategoriesWithProducts(): Flow<List<CategoryWithProducts>>
    
    // UPDATE
    @Update
    suspend fun update(category: Category)
    
    // DELETE
    @Delete
    suspend fun delete(category: Category)
    
    @Query("DELETE FROM category WHERE categoryId = :id")
    suspend fun deleteById(id: Int)
    
    @Query("DELETE FROM category")
    suspend fun deleteAll()
}
