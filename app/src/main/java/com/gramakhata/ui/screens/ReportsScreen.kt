package com.gramakhata.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gramakhata.ui.theme.*
import com.gramakhata.viewmodel.GramaViewModel

@Composable
fun ReportsScreen(viewModel: GramaViewModel) {
    val customers by viewModel.customers.collectAsState(initial = emptyList())
    
    val totalReceivable = customers.filter { it.totalDue > 0 }.sumOf { it.totalDue }
    val totalPayable = customers.filter { it.totalDue < 0 }.sumOf { Math.abs(it.totalDue) }
    
    val topDebtors = customers.sortedByDescending { it.totalDue }.take(5)

    Column(modifier = Modifier.fillMaxSize().background(Ivory).padding(24.dp)) {
        Text("Business Reports", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Forest)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ReportStatCard(title = "TOTAL CREDIT", amount = totalReceivable, color = DebitRed, modifier = Modifier.weight(1f))
            ReportStatCard(title = "TOTAL ADVANCE", amount = totalPayable, color = CreditGreen, modifier = Modifier.weight(1f))
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // AI Insight Placeholder
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            color = Forest.copy(alpha = 0.05f)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(32.dp).background(Forest, RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) {
                        Text("✨", color = Color.White, fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("AI Business Insight", fontWeight = FontWeight.Bold, color = Forest)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "\"Keep accurate records of all transactions for better financial clarity.\"",
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    color = Forest.copy(alpha = 0.7f)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text("TOP LEDGERS", color = Forest.copy(alpha = 0.4f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column {
                topDebtors.forEachIndexed { index, customer ->
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(40.dp).background(Forest.copy(alpha = 0.05f), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                                Text(customer.name.take(1), fontWeight = FontWeight.Bold, color = Forest)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(customer.name, fontWeight = FontWeight.Bold, color = Forest)
                        }
                        Text("₹${Math.abs(customer.totalDue)}", fontWeight = FontWeight.Bold, color = if (customer.totalDue > 0) DebitRed else CreditGreen)
                    }
                    if (index < topDebtors.size - 1) HorizontalDivider(color = Color.Black.copy(alpha = 0.03f))
                }
                if (topDebtors.isEmpty()) {
                    Text("No data available", modifier = Modifier.padding(32.dp).fillMaxWidth(), color = Forest.copy(alpha = 0.3f), fontStyle = FontStyle.Italic, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                }
            }
        }
    }
}

@Composable
fun ReportStatCard(title: String, amount: Long, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(title, color = Forest.copy(alpha = 0.4f), fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text("₹$amount", color = color, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}
