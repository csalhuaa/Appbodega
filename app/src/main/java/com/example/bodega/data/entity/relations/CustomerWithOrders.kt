package com.example.bodega.data.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.bodega.data.entity.Customer
import com.example.bodega.data.entity.Order

// Relación 1-n: Un cliente tiene muchas órdenes
data class CustomerWithOrders(
    @Embedded val customer: Customer,
    @Relation(
        parentColumn = "customerId",
        entityColumn = "customerId"
    )
    val orders: List<Order>
)
