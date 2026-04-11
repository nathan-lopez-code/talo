package com.hiven.talo.data.repository

import com.hiven.talo.data.local.dao.ProductDao
import com.hiven.talo.data.local.dao.SaleDao
import com.hiven.talo.data.local.entity.SaleEntity
import com.hiven.talo.data.local.entity.SaleItemEntity
import com.hiven.talo.ui.screens.sale.CartItemUi

class SaleRepository(
    private val saleDao: SaleDao,
    private val productDao: ProductDao
) {

    suspend fun confirmCashSale(
        cartItems: List<CartItemUi>
    ) {
        if (cartItems.isEmpty()) return

        val totalAmount = cartItems.sumOf { it.lineTotal }

        val saleId = saleDao.insertSale(
            SaleEntity(
                totalAmount = totalAmount,
                paymentType = "CASH"
            )
        )

        val saleItems = cartItems.map {
            SaleItemEntity(
                saleId = saleId,
                productId = it.productId,
                productName = it.productName,
                quantity = it.quantity,
                unitPrice = it.unitPrice,
                lineTotal = it.lineTotal
            )
        }

        saleDao.insertSaleItems(saleItems)

        cartItems.forEach { item ->
            val product = productDao.getById(item.productId)
            if (product != null) {
                val newStock = (product.stockQuantity - item.quantity).coerceAtLeast(0)
                productDao.updateStock(item.productId, newStock)
            }
        }
    }
}