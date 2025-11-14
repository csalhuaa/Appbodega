package com.example.bodega.data.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.bodega.data.entity.Order
import com.example.bodega.data.entity.OrderDetail

// Relación 1-n: Una orden tiene muchos detalles
data class OrderWithDetails(
    @Embedded val order: Order,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "orderId"
    )
    val orderDetails: List<OrderDetail>
)
