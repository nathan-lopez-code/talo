package com.hiven.talo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sales")
data class SaleEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val totalAmount: Double,
    val paymentType: String, //CASH or CREDIT
    val createdAt: Long = System.currentTimeMillis()
)
