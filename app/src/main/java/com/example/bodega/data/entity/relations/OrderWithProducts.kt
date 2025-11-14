package com.example.bodega.data.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.bodega.data.entity.Order
import com.example.bodega.data.entity.OrderDetail
import com.example.bodega.data.entity.Product

// Relación n-m: Una orden tiene muchos productos a través de OrderDetail
data class OrderWithProducts(
    @Embedded val order: Order,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "productId",
        associateBy = Junction(
            value = OrderDetail::class,
            parentColumn = "orderId",
            entityColumn = "productId"
        )
    )
    val products: List<Product>
)
