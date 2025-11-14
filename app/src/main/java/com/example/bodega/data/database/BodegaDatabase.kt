package com.example.bodega.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bodega.data.dao.*
import com.example.bodega.data.entity.*

@Database(
    entities = [
        Customer::class,
        Category::class,
        Product::class,
        Order::class,
        OrderDetail::class
    ],
    version = 1,
    exportSchema = false
)
abstract class BodegaDatabase : RoomDatabase() {
    
    abstract fun customerDao(): CustomerDao
    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao
    abstract fun orderDao(): OrderDao
    abstract fun orderDetailDao(): OrderDetailDao
    
    companion object {
        @Volatile
        private var INSTANCE: BodegaDatabase? = null
        
        fun getDatabase(context: Context): BodegaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BodegaDatabase::class.java,
                    "bodega_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
