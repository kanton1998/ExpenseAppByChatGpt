package com.example.expenseappbychatgpt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.OutlinedTextField
import com.example.expenseappbychatgpt.ui.theme.ExpenseAppByChatGptTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseAppByChatGptTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    var expenses by remember { mutableStateOf<List<Expense>>(emptyList()) }

    var isAddExpenseDialogVisible by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        MainScreen(
            scaffoldState = scaffoldState,
            expenses = expenses,
            onAddExpense = {
                isAddExpenseDialogVisible = true
            },
            onSortExpenses = { /* Handle sort expenses */ },
            onExpenseAdded = { newExpense ->
                expenses = expenses + listOf(newExpense)
                isAddExpenseDialogVisible = false
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("Added Expense: $newExpense")
                }
            }
        )

        if (isAddExpenseDialogVisible) {
            AddExpenseDialog(
                onDismiss = { isAddExpenseDialogVisible = false },
                onExpenseAdded = { newExpense ->
                    onExpenseAdded(newExpense)
                }
            )
        }
    }
}

fun onExpenseAdded(newExpense: Expense) {

}


@Composable
fun AddExpenseDialog(
    onDismiss: () -> Unit,
    onExpenseAdded: (Expense) -> Unit
) {
    var category by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Expense") },
        buttons = {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        val newExpense = Expense(category, amount, description)
                        onExpenseAdded(newExpense)
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Add")
                }

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Cancel")
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    )
}

@Composable
fun MainScreen(
    scaffoldState: ScaffoldState,
    expenses: List<Expense>,
    onAddExpense: () -> Unit,
    onSortExpenses: () -> Unit,
    onExpenseAdded: (Expense) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Expense Tracker") },
                actions = {
                    IconButton(onClick = onSortExpenses) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddExpense,
                modifier = Modifier
                    .padding(16.dp)
                    .background(MaterialTheme.colors.primary)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            ExpenseList(expenses)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}



@Composable
fun ExpenseList(expenses: List<Expense>) {
    LazyColumn {
        items(expenses) { expense ->
            ExpenseItem(expense = expense)
        }
    }
}

@Composable
fun ExpenseItem(expense: Expense) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Category: ${expense.category}")
            Text(text = "Amount: ${expense.amount}")
            Text(text = "Description: ${expense.description}")
        }
    }
}

data class Expense(
    val category: String = "",
    val amount: String = "",
    val description: String = ""
) {
    fun isValid(): Boolean {
        return category.isNotBlank() && amount.isNotBlank() && description.isNotBlank()
    }
}

fun createDummyExpense(index: Int): Expense {
    return Expense(
        category = "Category $index",
        amount = (index * 100).toString(),
        description = "Expense $index"
    )
}

