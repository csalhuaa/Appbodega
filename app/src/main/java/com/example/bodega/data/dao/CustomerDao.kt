package com.example.bodega.data.dao

import androidx.room.*
import com.example.bodega.data.entity.Customer
import com.example.bodega.data.entity.relations.CustomerWithOrders
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(customer: Customer): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(customers: List<Customer>)
    
    // READ
    @Query("SELECT * FROM customer")
    fun getAllCustomers(): Flow<List<Customer>>
    
    @Query("SELECT * FROM customer WHERE customerId = :id")
    suspend fun getCustomerById(id: Int): Customer?
    
    @Query("SELECT * FROM customer WHERE email = :email")
    suspend fun getCustomerByEmail(email: String): Customer?
    
    @Query("SELECT * FROM customer WHERE firstName LIKE '%' || :query || '%' OR lastName LIKE '%' || :query || '%'")
    fun searchCustomers(query: String): Flow<List<Customer>>
    
    // Consulta 1-n: Cliente con sus órdenes
    @Transaction
    @Query("SELECT * FROM customer WHERE customerId = :customerId")
    suspend fun getCustomerWithOrders(customerId: Int): CustomerWithOrders?
    
    @Transaction
    @Query("SELECT * FROM customer")
    fun getAllCustomersWithOrders(): Flow<List<CustomerWithOrders>>
    
    // UPDATE
    @Update
    suspend fun update(customer: Customer)
    
    // DELETE
    @Delete
    suspend fun delete(customer: Customer)
    
    @Query("DELETE FROM customer WHERE customerId = :id")
    suspend fun deleteById(id: Int)
    
    @Query("DELETE FROM customer")
    suspend fun deleteAll()
}
