package com.hiven.talo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hiven.talo.data.local.dao.ProductDao
import com.hiven.talo.data.local.dao.SaleDao
import com.hiven.talo.data.local.entity.ProductEntity
import com.hiven.talo.data.local.entity.SaleEntity
import com.hiven.talo.data.local.entity.SaleItemEntity

@Database(
    entities = [
        ProductEntity::class,
        SaleEntity::class,
        SaleItemEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun saleDao(): SaleDao
}