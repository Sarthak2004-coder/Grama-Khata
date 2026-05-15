package com.gramakhata.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gramakhata.data.AppDatabase
import com.gramakhata.data.Customer
import com.gramakhata.data.ShopSettings
import com.gramakhata.data.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class GramaViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val dao = db.dao()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val customers: Flow<List<Customer>> = _searchQuery.flatMapLatest { query ->
        if (query.isEmpty()) dao.getAllCustomers()
        else dao.searchCustomers(query)
    }

    val settings: Flow<ShopSettings?> = dao.getSettings()
    val allTransactions: Flow<List<Transaction>> = dao.getAllTransactions()

    init {
        viewModelScope.launch {
            // Initialize default settings if none exist
            dao.getSettings().collect {
                if (it == null) {
                    dao.insertSettings(ShopSettings(
                        shopName = "My Grama Shop",
                        ownerName = "Shopkeeper",
                        language = "en",
                        theme = "light"
                    ))
                }
            }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun addEntry(name: String, phone: String, amount: Long, type: String, photoUrl: String? = null) {
        viewModelScope.launch {
            var customer = dao.getCustomerByName(name)
            if (customer == null) {
                val id = dao.insertCustomer(
                    Customer(
                        name = name,
                        phone = phone,
                        photoUrl = photoUrl,
                        lastTransactionAt = System.currentTimeMillis()
                    )
                )
                customer = dao.getCustomerById(id.toInt())
            }

            customer?.let {
                val txAmount = if (type == "give") amount else -amount
                val transaction = Transaction(
                    customerId = it.id,
                    amount = txAmount,
                    type = type,
                    date = System.currentTimeMillis()
                )
                dao.addTransactionAndUpdateCustomer(transaction)
            }
        }
    }

    suspend fun getOrCreateCustomer(name: String, phone: String): Customer {
        // Implementation for getting or creating customer
        return Customer(name = name, phone = phone, lastTransactionAt = System.currentTimeMillis())
    }

    fun insertTransaction(transaction: Transaction) {
        viewModelScope.launch {
            dao.addTransactionAndUpdateCustomer(transaction)
        }
    }

    fun addCustomerAndTransaction(name: String, phone: String, amount: Long, type: String) {
        viewModelScope.launch {
            // Simplified logic: find by name first
            // This should ideally be handled in a Repository
            val existing = dao.getAllCustomers().flatMapLatest { list -> 
                kotlinx.coroutines.flow.flowOf(list.find { it.name.equals(name, ignoreCase = true) })
            }
            // For now, let's just use a more direct approach in the UI or here
        }
    }
    
    fun getTransactionsForCustomer(customerId: Int): Flow<List<Transaction>> {
        return dao.getTransactionsForCustomer(customerId)
    }

    fun updateSettings(settings: ShopSettings) {
        viewModelScope.launch {
            dao.updateSettings(settings)
        }
    }

    fun clearData() {
        viewModelScope.launch {
            dao.clearAllCustomers()
            dao.clearAllTransactions()
        }
    }
    
    suspend fun findCustomerByName(name: String): Customer? {
        // Need a DAO method for this
        return null 
    }
    
    suspend fun insertCustomer(customer: Customer): Long {
        return dao.insertCustomer(customer)
    }
}
