package com.gramakhata.ui.screens

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.LocalTextStyle
import coil.compose.rememberAsyncImagePainter
import com.gramakhata.data.Customer
import com.gramakhata.ui.theme.*
import com.gramakhata.viewmodel.GramaViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: GramaViewModel,
    onSelectCustomer: (Customer) -> Unit,
    onAddTransaction: () -> Unit
) {
    val customers by viewModel.customers.collectAsState(initial = emptyList())
    val searchQuery by viewModel.searchQuery.collectAsState()
    val settings by viewModel.settings.collectAsState(initial = null)
    
    val totalReceivable = customers.filter { it.totalDue > 0 }.sumOf { it.totalDue }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTransaction,
                containerColor = Forest,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(bottom = 80.dp) // Space for bottom nav
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Ivory)
                .padding(padding)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Hello, ${settings?.ownerName ?: "Shopkeeper"}", color = Forest.copy(alpha = 0.6f), fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    Text(settings?.shopName ?: "Grama-Khata Dashboard", color = Forest, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Saffron.copy(alpha = 0.2f))
                        .padding(2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(settings?.ownerName?.take(1) ?: "S", color = Saffron, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Summary Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(160.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Forest)
            ) {
                Box(modifier = Modifier.fillMaxSize().padding(24.dp)) {
                    Column {
                        Text("TOTAL RECEIVABLE (ಒಟ್ಟು ಬಾಕಿ)", color = Color.White.copy(alpha = 0.8f), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        
                        val animatedTotal by animateIntAsState(
                            targetValue = totalReceivable.toInt(),
                            animationSpec = tween(durationMillis = 1000)
                        )
                        
                        Text("₹$animatedTotal", color = Saffron, fontSize = 36.sp, fontWeight = FontWeight.Bold)
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                color = Color.White.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color.White)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("${customers.size} Ledgers", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Text("Last updated: Today", color = Color.White.copy(alpha = 0.5f), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Search
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                textStyle = LocalTextStyle.current.copy(color = Forest),
                placeholder = { Text("Search customers...", color = Forest.copy(alpha = 0.4f)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Forest.copy(alpha = 0.4f)) },
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White,
                    unfocusedBorderColor = Forest.copy(alpha = 0.05f),
                    focusedBorderColor = Saffron,
                    cursorColor = Forest
                )
            )

            // Customer List
            Text(
                "ACTIVE LEDGERS",
                modifier = Modifier.padding(horizontal = 24.dp),
                color = Forest.copy(alpha = 0.4f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(customers) { customer ->
                    CustomerItem(customer, onClick = { onSelectCustomer(customer) })
                }
            }
        }
    }
}

@Composable
fun CustomerItem(customer: Customer, onClick: () -> Unit) {
    val borderColor = if (customer.totalDue > 0) DebitRed else CreditGreen
    val amountColor = if (customer.totalDue > 0) DebitRed else CreditGreen
    val statusText = if (customer.totalDue > 0) "Overdue" else "Settled"
    
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val dateStr = sdf.format(Date(customer.lastTransactionAt))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Border/Indicator
            Box(modifier = Modifier.width(4.dp).height(40.dp).background(borderColor, RoundedCornerShape(2.dp)))
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Forest.copy(alpha = 0.05f)),
                contentAlignment = Alignment.Center
            ) {
                if (customer.photoUrl != null) {
                    androidx.compose.foundation.Image(
                        painter = rememberAsyncImagePainter(customer.photoUrl),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Forest)
                }
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(customer.name, fontWeight = FontWeight.Bold, color = Forest, fontSize = 16.sp)
                Text("Last activity: $dateStr", color = Forest.copy(alpha = 0.4f), fontSize = 11.sp)
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text("₹${Math.abs(customer.totalDue)}", fontWeight = FontWeight.Bold, color = amountColor, fontSize = 18.sp)
                Text(statusText.uppercase(), color = amountColor.copy(alpha = 0.7f), fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
