package com.example.bodega.data.repository

import com.example.bodega.data.dao.CustomerDao
import com.example.bodega.data.entity.Customer
import com.example.bodega.data.entity.relations.CustomerWithOrders
import kotlinx.coroutines.flow.Flow

class CustomerRepository(private val customerDao: CustomerDao) {
    
    // CREATE
    suspend fun insert(customer: Customer): Long = customerDao.insert(customer)
    suspend fun insertAll(customers: List<Customer>) = customerDao.insertAll(customers)
    
    // READ
    fun getAllCustomers(): Flow<List<Customer>> = customerDao.getAllCustomers()
    suspend fun getCustomerById(id: Int): Customer? = customerDao.getCustomerById(id)
    suspend fun getCustomerByEmail(email: String): Customer? = customerDao.getCustomerByEmail(email)
    fun searchCustomers(query: String): Flow<List<Customer>> = customerDao.searchCustomers(query)
    
    // Relaciones 1-n
    suspend fun getCustomerWithOrders(customerId: Int): CustomerWithOrders? = 
        customerDao.getCustomerWithOrders(customerId)
    fun getAllCustomersWithOrders(): Flow<List<CustomerWithOrders>> = 
        customerDao.getAllCustomersWithOrders()
    
    // UPDATE
    suspend fun update(customer: Customer) = customerDao.update(customer)
    
    // DELETE
    suspend fun delete(customer: Customer) = customerDao.delete(customer)
    suspend fun deleteById(id: Int) = customerDao.deleteById(id)
    suspend fun deleteAll() = customerDao.deleteAll()
}
