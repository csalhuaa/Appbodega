package com.example.bodega.data.dao

import androidx.room.*
import com.example.bodega.data.entity.OrderDetail
import com.example.bodega.data.entity.relations.OrderDetailWithProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDetailDao {
    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(orderDetail: OrderDetail)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(orderDetails: List<OrderDetail>)
    
    // READ
    @Query("SELECT * FROM order_detail")
    fun getAllOrderDetails(): Flow<List<OrderDetail>>
    
    @Query("SELECT * FROM order_detail WHERE orderId = :orderId")
    fun getOrderDetailsByOrder(orderId: Int): Flow<List<OrderDetail>>
    
    @Query("SELECT * FROM order_detail WHERE productId = :productId")
    fun getOrderDetailsByProduct(productId: Int): Flow<List<OrderDetail>>
    
    @Query("SELECT * FROM order_detail WHERE orderId = :orderId AND productId = :productId")
    suspend fun getOrderDetail(orderId: Int, productId: Int): OrderDetail?
    
    // Consulta con producto incluido
    @Transaction
    @Query("SELECT * FROM order_detail WHERE orderId = :orderId")
    fun getOrderDetailsWithProducts(orderId: Int): Flow<List<OrderDetailWithProduct>>
    
    @Transaction
    @Query("SELECT * FROM order_detail")
    fun getAllOrderDetailsWithProducts(): Flow<List<OrderDetailWithProduct>>
    
    // Consultas agregadas
    @Query("SELECT SUM(quantity) FROM order_detail WHERE orderId = :orderId")
    suspend fun getTotalQuantityByOrder(orderId: Int): Int?
    
    @Query("SELECT SUM(quantity) FROM order_detail WHERE productId = :productId")
    suspend fun getTotalQuantityByProduct(productId: Int): Int?
    
    // UPDATE
    @Update
    suspend fun update(orderDetail: OrderDetail)
    
    @Query("UPDATE order_detail SET quantity = :quantity WHERE orderId = :orderId AND productId = :productId")
    suspend fun updateQuantity(orderId: Int, productId: Int, quantity: Int)
    
    // DELETE
    @Delete
    suspend fun delete(orderDetail: OrderDetail)
    
    @Query("DELETE FROM order_detail WHERE orderId = :orderId AND productId = :productId")
    suspend fun deleteByIds(orderId: Int, productId: Int)
    
    @Query("DELETE FROM order_detail WHERE orderId = :orderId")
    suspend fun deleteByOrder(orderId: Int)
    
    @Query("DELETE FROM order_detail")
    suspend fun deleteAll()
}
