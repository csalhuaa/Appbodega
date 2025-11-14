package com.example.bodega.data.repository

import com.example.bodega.data.dao.ProductDao
import com.example.bodega.data.entity.Product
import com.example.bodega.data.entity.relations.ProductWithOrders
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {
    
    // CREATE
    suspend fun insert(product: Product): Long = productDao.insert(product)
    suspend fun insertAll(products: List<Product>) = productDao.insertAll(products)
    
    // READ
    fun getAllProducts(): Flow<List<Product>> = productDao.getAllProducts()
    suspend fun getProductById(id: Int): Product? = productDao.getProductById(id)
    fun getProductsByCategory(categoryId: Int): Flow<List<Product>> = 
        productDao.getProductsByCategory(categoryId)
    fun searchProducts(query: String): Flow<List<Product>> = productDao.searchProducts(query)
    fun getProductsByPriceRange(minPrice: Double, maxPrice: Double): Flow<List<Product>> = 
        productDao.getProductsByPriceRange(minPrice, maxPrice)
    
    // Relaciones n-m
    suspend fun getProductWithOrders(productId: Int): ProductWithOrders? = 
        productDao.getProductWithOrders(productId)
    fun getAllProductsWithOrders(): Flow<List<ProductWithOrders>> = 
        productDao.getAllProductsWithOrders()
    
    // UPDATE
    suspend fun update(product: Product) = productDao.update(product)
    
    // DELETE
    suspend fun delete(product: Product) = productDao.delete(product)
    suspend fun deleteById(id: Int) = productDao.deleteById(id)
    suspend fun deleteAll() = productDao.deleteAll()
}
