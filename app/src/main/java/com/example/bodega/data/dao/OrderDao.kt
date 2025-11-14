package com.example.bodega.data.dao

import androidx.room.*
import com.example.bodega.data.entity.Order
import com.example.bodega.data.entity.relations.OrderWithDetails
import com.example.bodega.data.entity.relations.OrderWithProducts
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: Order): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(orders: List<Order>)
    
    // READ
    @Query("SELECT * FROM order_table")
    fun getAllOrders(): Flow<List<Order>>
    
    @Query("SELECT * FROM order_table WHERE orderId = :id")
    suspend fun getOrderById(id: Int): Order?
    
    @Query("SELECT * FROM order_table WHERE customerId = :customerId")
    fun getOrdersByCustomer(customerId: Int): Flow<List<Order>>
    
    @Query("SELECT * FROM order_table WHERE orderDate BETWEEN :startDate AND :endDate")
    fun getOrdersByDateRange(startDate: String, endDate: String): Flow<List<Order>>
    
    @Query("SELECT * FROM order_table ORDER BY orderDate DESC LIMIT :limit")
    fun getRecentOrders(limit: Int): Flow<List<Order>>
    
    // Consulta 1-n: Orden con sus detalles
    @Transaction
    @Query("SELECT * FROM order_table WHERE orderId = :orderId")
    suspend fun getOrderWithDetails(orderId: Int): OrderWithDetails?
    
    @Transaction
    @Query("SELECT * FROM order_table")
    fun getAllOrdersWithDetails(): Flow<List<OrderWithDetails>>
    
    // Consulta n-m: Orden con sus productos
    @Transaction
    @Query("SELECT * FROM order_table WHERE orderId = :orderId")
    suspend fun getOrderWithProducts(orderId: Int): OrderWithProducts?
    
    @Transaction
    @Query("SELECT * FROM order_table")
    fun getAllOrdersWithProducts(): Flow<List<OrderWithProducts>>
    
    // UPDATE
    @Update
    suspend fun update(order: Order)
    
    // DELETE
    @Delete
    suspend fun delete(order: Order)
    
    @Query("DELETE FROM order_table WHERE orderId = :id")
    suspend fun deleteById(id: Int)
    
    @Query("DELETE FROM order_table")
    suspend fun deleteAll()
}
