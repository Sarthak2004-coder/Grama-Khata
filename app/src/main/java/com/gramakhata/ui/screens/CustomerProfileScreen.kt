package com.gramakhata.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.gramakhata.data.Customer
import com.gramakhata.data.Transaction
import com.gramakhata.ui.theme.*
import com.gramakhata.viewmodel.GramaViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CustomerProfileScreen(
    customer: Customer,
    viewModel: GramaViewModel,
    onBack: () -> Unit,
    onAddTx: (String) -> Unit
) {
    val transactions by viewModel.getTransactionsForCustomer(customer.id).collectAsState(initial = emptyList())
    val settings by viewModel.settings.collectAsState(initial = null)
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().background(Ivory)) {
        // Top Header Card
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Forest,
            shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    Text("CUSTOMER PROFILE", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Row {
                        IconButton(
                            onClick = { 
                                sendWhatsAppMessage(context, customer.phone, customer.totalDue, settings?.shopName ?: "Our Shop")
                            }, 
                            modifier = Modifier.background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                        ) {
                            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "WhatsApp", tint = Color.White)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = { 
                                sendSMSMessage(context, customer.phone, customer.totalDue, settings?.shopName ?: "Our Shop")
                            }, 
                            modifier = Modifier.background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                        ) {
                            Icon(Icons.Default.Email, contentDescription = "SMS", tint = Color.White)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = { 
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${customer.phone}"))
                                context.startActivity(intent)
                            }, 
                            modifier = Modifier.background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                        ) {
                            Icon(Icons.Default.Call, contentDescription = "Call", tint = Color.White)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Saffron),
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
                            Text(customer.name.take(1), color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(customer.name, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                        Text(customer.phone, color = Color.White.copy(alpha = 0.6f), fontSize = 14.sp)
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("TOTAL DUE AMOUNT", color = Color.White.copy(alpha = 0.4f), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        val dueColor = if (customer.totalDue > 0) Saffron else Color.White
                        Text("₹${Math.abs(customer.totalDue)}", color = dueColor, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                        Text(
                            if (customer.totalDue > 0) "OWES TO SHOP" else "PAID AS ADVANCE",
                            color = Color.White.copy(alpha = 0.4f),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(
                        onClick = { onAddTx("give") },
                        modifier = Modifier.weight(1f).height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Forest),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Give Item")
                    }
                    Button(
                        onClick = { onAddTx("take") },
                        modifier = Modifier.weight(1f).height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DebitRed),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Take Cash")
                    }
                }
            }

            item {
                Text("TRANSACTION HISTORY", color = Forest.copy(alpha = 0.4f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            items(transactions) { tx ->
                TransactionItem(tx)
            }
            
            if (transactions.isEmpty()) {
                item {
                    Text("No entries yet", modifier = Modifier.fillMaxWidth().padding(32.dp), color = Forest.copy(alpha = 0.3f), fontWeight = FontWeight.Medium)
                }
            }
            
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}

fun sendWhatsAppMessage(context: Context, phone: String, amount: Long, shopName: String) {
    if (phone.isBlank()) {
        Toast.makeText(context, "No phone number available", Toast.LENGTH_SHORT).show()
        return
    }
    
    val message = "Namaskara, your due at $shopName is Rs.$amount."
    val uri = Uri.parse("https://api.whatsapp.com/send?phone=91$phone&text=${Uri.encode(message)}")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "WhatsApp not found. Try SMS.", Toast.LENGTH_SHORT).show()
    }
}

fun sendSMSMessage(context: Context, phone: String, amount: Long, shopName: String) {
    if (phone.isBlank()) {
        Toast.makeText(context, "No phone number available", Toast.LENGTH_SHORT).show()
        return
    }
    
    val message = "Namaskara, your due at $shopName is Rs.$amount."
    val smsIntent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$phone?body=${Uri.encode(message)}"))
    try {
        context.startActivity(smsIntent)
    } catch (e: Exception) {
        Toast.makeText(context, "Could not open SMS app.", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun TransactionItem(tx: Transaction) {
    val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    val dateStr = sdf.format(Date(tx.date))
    val isGive = tx.amount > 0
    val amountColor = if (isGive) Forest else DebitRed

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(if (isGive) Forest.copy(alpha = 0.1f) else DebitRed.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(if (isGive) "↑" else "↓", color = if (isGive) Forest else DebitRed, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(if (isGive) "Item Given" else "Payment Received", fontWeight = FontWeight.Bold, color = Forest)
                    Text(dateStr, color = Forest.copy(alpha = 0.4f), fontSize = 10.sp)
                }
            }
            Text("₹${Math.abs(tx.amount)}", fontWeight = FontWeight.Bold, color = amountColor, fontSize = 18.sp)
        }
    }
}
