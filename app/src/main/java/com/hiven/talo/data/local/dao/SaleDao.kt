package com.hiven.talo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hiven.talo.data.local.entity.SaleEntity
import com.hiven.talo.data.local.entity.SaleItemEntity

@Dao
interface SaleDao {

    @Insert
    suspend fun insertSale(sale: SaleEntity): Long

    @Insert
    suspend fun insertSaleItems(items: List<SaleItemEntity>)

    @Query("SELECT * FROM sales ORDER BY createdAt DESC")
    suspend fun getAllSales(): List<SaleEntity>
}