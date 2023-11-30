package com.example.expenseappbychatgpt

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Expens::class], version = 1, exportSchema = false)
abstract class ExpensesDatabase : RoomDatabase() {
    abstract fun expensesDao(): ExpenseDao
}