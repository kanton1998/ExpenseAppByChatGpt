package com.example.expenseappbychatgpt

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses_table")
class Expens(
    @PrimaryKey(autoGenerate = true)
    val expenseId: Long,
    val category: String,
    val amount: Double,
    val description: String
) {
}