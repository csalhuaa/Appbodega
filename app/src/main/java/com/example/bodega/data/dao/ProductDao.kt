package com.example.bodega.data.dao

import androidx.room.*
import com.example.bodega.data.entity.Product
import com.example.bodega.data.entity.relations.ProductWithOrders
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)
    
    // READ
    @Query("SELECT * FROM product")
    fun getAllProducts(): Flow<List<Product>>
    
    @Query("SELECT * FROM product WHERE productId = :id")
    suspend fun getProductById(id: Int): Product?
    
    @Query("SELECT * FROM product WHERE categoryId = :categoryId")
    fun getProductsByCategory(categoryId: Int): Flow<List<Product>>
    
    @Query("SELECT * FROM product WHERE productName LIKE '%' || :query || '%'")
    fun searchProducts(query: String): Flow<List<Product>>
    
    @Query("SELECT * FROM product WHERE price BETWEEN :minPrice AND :maxPrice")
    fun getProductsByPriceRange(minPrice: Double, maxPrice: Double): Flow<List<Product>>
    
    // Consulta n-m: Producto con las órdenes en las que aparece
    @Transaction
    @Query("SELECT * FROM product WHERE productId = :productId")
    suspend fun getProductWithOrders(productId: Int): ProductWithOrders?
    
    @Transaction
    @Query("SELECT * FROM product")
    fun getAllProductsWithOrders(): Flow<List<ProductWithOrders>>
    
    // UPDATE
    @Update
    suspend fun update(product: Product)
    
    // DELETE
    @Delete
    suspend fun delete(product: Product)
    
    @Query("DELETE FROM product WHERE productId = :id")
    suspend fun deleteById(id: Int)
    
    @Query("DELETE FROM product")
    suspend fun deleteAll()
}
