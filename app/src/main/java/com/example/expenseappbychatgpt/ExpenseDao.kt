package com.example.expenseappbychatgpt

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insertExpense(expense: Expens)

    @Query("SELECT * FROM expenses_table")
    suspend fun getAllExpenses(): List<Expens>

}