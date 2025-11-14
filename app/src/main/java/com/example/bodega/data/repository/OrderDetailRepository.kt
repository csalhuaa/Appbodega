package com.example.bodega.data.repository

import com.example.bodega.data.dao.OrderDetailDao
import com.example.bodega.data.entity.OrderDetail
import com.example.bodega.data.entity.relations.OrderDetailWithProduct
import kotlinx.coroutines.flow.Flow

class OrderDetailRepository(private val orderDetailDao: OrderDetailDao) {
    
    // CREATE
    suspend fun insert(orderDetail: OrderDetail) = orderDetailDao.insert(orderDetail)
    suspend fun insertAll(orderDetails: List<OrderDetail>) = orderDetailDao.insertAll(orderDetails)
    
    // READ
    fun getAllOrderDetails(): Flow<List<OrderDetail>> = orderDetailDao.getAllOrderDetails()
    fun getOrderDetailsByOrder(orderId: Int): Flow<List<OrderDetail>> = 
        orderDetailDao.getOrderDetailsByOrder(orderId)
    fun getOrderDetailsByProduct(productId: Int): Flow<List<OrderDetail>> = 
        orderDetailDao.getOrderDetailsByProduct(productId)
    suspend fun getOrderDetail(orderId: Int, productId: Int): OrderDetail? = 
        orderDetailDao.getOrderDetail(orderId, productId)
    
    // Relaciones
    fun getOrderDetailsWithProducts(orderId: Int): Flow<List<OrderDetailWithProduct>> = 
        orderDetailDao.getOrderDetailsWithProducts(orderId)
    fun getAllOrderDetailsWithProducts(): Flow<List<OrderDetailWithProduct>> = 
        orderDetailDao.getAllOrderDetailsWithProducts()
    
    // Consultas agregadas
    suspend fun getTotalQuantityByOrder(orderId: Int): Int? = 
        orderDetailDao.getTotalQuantityByOrder(orderId)
    suspend fun getTotalQuantityByProduct(productId: Int): Int? = 
        orderDetailDao.getTotalQuantityByProduct(productId)
    
    // UPDATE
    suspend fun update(orderDetail: OrderDetail) = orderDetailDao.update(orderDetail)
    suspend fun updateQuantity(orderId: Int, productId: Int, quantity: Int) = 
        orderDetailDao.updateQuantity(orderId, productId, quantity)
    
    // DELETE
    suspend fun delete(orderDetail: OrderDetail) = orderDetailDao.delete(orderDetail)
    suspend fun deleteByIds(orderId: Int, productId: Int) = 
        orderDetailDao.deleteByIds(orderId, productId)
    suspend fun deleteByOrder(orderId: Int) = orderDetailDao.deleteByOrder(orderId)
    suspend fun deleteAll() = orderDetailDao.deleteAll()
}
