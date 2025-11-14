package com.example.bodega.data.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.bodega.data.entity.Order
import com.example.bodega.data.entity.OrderDetail
import com.example.bodega.data.entity.Product

// Relación n-m: Un producto puede estar en muchas órdenes
data class ProductWithOrders(
    @Embedded val product: Product,
    @Relation(
        parentColumn = "productId",
        entityColumn = "orderId",
        associateBy = Junction(
            value = OrderDetail::class,
            parentColumn = "productId",
            entityColumn = "orderId"
        )
    )
    val orders: List<Order>
)
