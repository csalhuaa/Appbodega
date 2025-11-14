package com.example.bodega.data.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.bodega.data.entity.Category
import com.example.bodega.data.entity.Product

// Relación 1-n: Una categoría tiene muchos productos
data class CategoryWithProducts(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryId"
    )
    val products: List<Product>
)
