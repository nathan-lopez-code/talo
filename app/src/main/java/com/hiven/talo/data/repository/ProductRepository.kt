package com.hiven.talo.data.repository

import com.hiven.talo.data.local.dao.ProductDao
import com.hiven.talo.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

class ProductRepository(
    private val productDao: ProductDao
) {
    fun observeProducts(): Flow<List<ProductEntity>> = productDao.observeProducts()

    suspend fun addProduct(
        name: String,
        price: Double,
        stockQuantity: Int
    ) {
        productDao.insert(
            ProductEntity(
                name = name,
                price = price,
                stockQuantity = stockQuantity
            )
        )
    }
}