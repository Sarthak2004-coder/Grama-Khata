package com.gramakhata.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GramaKhataDao {
    // Customers
    @Query("SELECT * FROM customers WHERE name = :name LIMIT 1")
    suspend fun getCustomerByName(name: String): Customer?

    @Query("SELECT * FROM customers ORDER BY totalDue DESC")

    fun getAllCustomers(): Flow<List<Customer>>

    @Query("SELECT * FROM customers WHERE name LIKE :search || '%' ORDER BY totalDue DESC")
    fun searchCustomers(search: String): Flow<List<Customer>>

    @Query("SELECT * FROM customers WHERE id = :id")
    suspend fun getCustomerById(id: Int): Customer?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: Customer): Long

    @Update
    suspend fun updateCustomer(customer: Customer)

    @Query("DELETE FROM customers")
    suspend fun clearAllCustomers()

    // Transactions
    @Query("SELECT * FROM transactions WHERE customerId = :customerId ORDER BY date DESC")
    fun getTransactionsForCustomer(customerId: Int): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Insert
    suspend fun insertTransaction(transaction: Transaction)

    @Query("DELETE FROM transactions")
    suspend fun clearAllTransactions()

    // Settings
    @Query("SELECT * FROM settings LIMIT 1")
    fun getSettings(): Flow<ShopSettings?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: ShopSettings)

    @Update
    suspend fun updateSettings(settings: ShopSettings)

    @androidx.room.Transaction
    suspend fun addTransactionAndUpdateCustomer(transaction: Transaction) {
        insertTransaction(transaction)
        val customer = getCustomerById(transaction.customerId)
        customer?.let {
            val newTotal = it.totalDue + transaction.amount
            updateCustomer(it.copy(totalDue = newTotal, lastTransactionAt = transaction.date))
        }
    }
}
