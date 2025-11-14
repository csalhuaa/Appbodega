package com.example.bodega.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    val customerId: Int = 0,
    val firstName: String,
    val lastName: String,
    val email: String
)
