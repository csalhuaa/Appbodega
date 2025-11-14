package com.example.bodega.data.repository

import com.example.bodega.data.dao.OrderDao
import com.example.bodega.data.entity.Order
import com.example.bodega.data.entity.relations.OrderWithDetails
import com.example.bodega.data.entity.relations.OrderWithProducts
import kotlinx.coroutines.flow.Flow

class OrderRepository(private val orderDao: OrderDao) {
    
    // CREATE
    suspend fun insert(order: Order): Long = orderDao.insert(order)
    suspend fun insertAll(orders: List<Order>) = orderDao.insertAll(orders)
    
    // READ
    fun getAllOrders(): Flow<List<Order>> = orderDao.getAllOrders()
    suspend fun getOrderById(id: Int): Order? = orderDao.getOrderById(id)
    fun getOrdersByCustomer(customerId: Int): Flow<List<Order>> = 
        orderDao.getOrdersByCustomer(customerId)
    fun getOrdersByDateRange(startDate: String, endDate: String): Flow<List<Order>> = 
        orderDao.getOrdersByDateRange(startDate, endDate)
    fun getRecentOrders(limit: Int): Flow<List<Order>> = orderDao.getRecentOrders(limit)
    
    // Relaciones 1-n
    suspend fun getOrderWithDetails(orderId: Int): OrderWithDetails? = 
        orderDao.getOrderWithDetails(orderId)
    fun getAllOrdersWithDetails(): Flow<List<OrderWithDetails>> = 
        orderDao.getAllOrdersWithDetails()
    
    // Relaciones n-m
    suspend fun getOrderWithProducts(orderId: Int): OrderWithProducts? = 
        orderDao.getOrderWithProducts(orderId)
    fun getAllOrdersWithProducts(): Flow<List<OrderWithProducts>> = 
        orderDao.getAllOrdersWithProducts()
    
    // UPDATE
    suspend fun update(order: Order) = orderDao.update(order)
    
    // DELETE
    suspend fun delete(order: Order) = orderDao.delete(order)
    suspend fun deleteById(id: Int) = orderDao.deleteById(id)
    suspend fun deleteAll() = orderDao.deleteAll()
}
