package com.example.bodega.data.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.bodega.data.entity.OrderDetail
import com.example.bodega.data.entity.Product

// Información completa del detalle de orden con producto
data class OrderDetailWithProduct(
    @Embedded val orderDetail: OrderDetail,
    @Relation(
        parentColumn = "productId",
        entityColumn = "productId"
    )
    val product: Product
)
