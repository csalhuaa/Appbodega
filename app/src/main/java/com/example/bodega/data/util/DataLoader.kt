package com.example.bodega.data.util

import android.content.Context
import com.example.bodega.data.database.BodegaDatabase
import com.example.bodega.data.entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Utilidad para cargar datos iniciales desde archivos en la carpeta assets
 * Los archivos deben estar en formato CSV con la siguiente estructura:
 * 
 * customers.csv: firstName,lastName,email
 * categories.csv: categoryName
 * products.csv: productName,price,categoryId
 * orders.csv: customerId,orderDate
 * order_details.csv: orderId,productId,quantity
 */
class DataLoader(private val context: Context) {
    
    private val database = BodegaDatabase.getDatabase(context)
    
    /**
     * Carga todos los datos desde archivos CSV en la carpeta assets
     */
    suspend fun loadAllData() = withContext(Dispatchers.IO) {
        try {
            loadCustomers()
            loadCategories()
            loadProducts()
            loadOrders()
            loadOrderDetails()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Error al cargar datos: ${e.message}")
        }
    }
    
    /**
     * Carga clientes desde customers.csv
     * Formato: firstName,lastName,email
     */
    suspend fun loadCustomers() = withContext(Dispatchers.IO) {
        try {
            val customers = mutableListOf<Customer>()
            val inputStream = context.assets.open("customers.csv")
            val reader = BufferedReader(InputStreamReader(inputStream))
            
            reader.useLines { lines ->
                lines.drop(1).forEach { line -> // Skip header
                    val parts = line.split(",")
                    if (parts.size >= 3) {
                        customers.add(
                            Customer(
                                firstName = parts[0].trim(),
                                lastName = parts[1].trim(),
                                email = parts[2].trim()
                            )
                        )
                    }
                }
            }
            
            if (customers.isNotEmpty()) {
                database.customerDao().insertAll(customers)
            }
        } catch (e: Exception) {
            // Archivo no existe o error al leer
            e.printStackTrace()
        }
    }
    
    /**
     * Carga categorías desde categories.csv
     * Formato: categoryName
     */
    suspend fun loadCategories() = withContext(Dispatchers.IO) {
        try {
            val categories = mutableListOf<Category>()
            val inputStream = context.assets.open("categories.csv")
            val reader = BufferedReader(InputStreamReader(inputStream))
            
            reader.useLines { lines ->
                lines.drop(1).forEach { line -> // Skip header
                    val categoryName = line.trim()
                    if (categoryName.isNotEmpty()) {
                        categories.add(Category(categoryName = categoryName))
                    }
                }
            }
            
            if (categories.isNotEmpty()) {
                database.categoryDao().insertAll(categories)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * Carga productos desde products.csv
     * Formato: productName,price,categoryId
     */
    suspend fun loadProducts() = withContext(Dispatchers.IO) {
        try {
            val products = mutableListOf<Product>()
            val inputStream = context.assets.open("products.csv")
            val reader = BufferedReader(InputStreamReader(inputStream))
            
            reader.useLines { lines ->
                lines.drop(1).forEach { line -> // Skip header
                    val parts = line.split(",")
                    if (parts.size >= 3) {
                        products.add(
                            Product(
                                productName = parts[0].trim(),
                                price = parts[1].trim().toDoubleOrNull() ?: 0.0,
                                categoryId = parts[2].trim().toIntOrNull() ?: 1
                            )
                        )
                    }
                }
            }
            
            if (products.isNotEmpty()) {
                database.productDao().insertAll(products)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * Carga órdenes desde orders.csv
     * Formato: customerId,orderDate
     */
    suspend fun loadOrders() = withContext(Dispatchers.IO) {
        try {
            val orders = mutableListOf<Order>()
            val inputStream = context.assets.open("orders.csv")
            val reader = BufferedReader(InputStreamReader(inputStream))
            
            reader.useLines { lines ->
                lines.drop(1).forEach { line -> // Skip header
                    val parts = line.split(",")
                    if (parts.size >= 2) {
                        orders.add(
                            Order(
                                customerId = parts[0].trim().toIntOrNull() ?: 1,
                                orderDate = parts[1].trim()
                            )
                        )
                    }
                }
            }
            
            if (orders.isNotEmpty()) {
                database.orderDao().insertAll(orders)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * Carga detalles de órdenes desde order_details.csv
     * Formato: orderId,productId,quantity
     */
    suspend fun loadOrderDetails() = withContext(Dispatchers.IO) {
        try {
            val orderDetails = mutableListOf<OrderDetail>()
            val inputStream = context.assets.open("order_details.csv")
            val reader = BufferedReader(InputStreamReader(inputStream))
            
            reader.useLines { lines ->
                lines.drop(1).forEach { line -> // Skip header
                    val parts = line.split(",")
                    if (parts.size >= 3) {
                        orderDetails.add(
                            OrderDetail(
                                orderId = parts[0].trim().toIntOrNull() ?: 1,
                                productId = parts[1].trim().toIntOrNull() ?: 1,
                                quantity = parts[2].trim().toIntOrNull() ?: 1
                            )
                        )
                    }
                }
            }
            
            if (orderDetails.isNotEmpty()) {
                database.orderDetailDao().insertAll(orderDetails)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * Limpia toda la base de datos
     */
    suspend fun clearAllData() = withContext(Dispatchers.IO) {
        database.orderDetailDao().deleteAll()
        database.orderDao().deleteAll()
        database.productDao().deleteAll()
        database.categoryDao().deleteAll()
        database.customerDao().deleteAll()
    }
}
