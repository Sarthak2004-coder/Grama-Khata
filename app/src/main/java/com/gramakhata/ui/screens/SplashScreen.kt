package com.gramakhata.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gramakhata.ui.theme.Forest
import com.gramakhata.ui.theme.Ivory
import com.gramakhata.ui.theme.Saffron
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinish: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2500)
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Forest),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(130.dp, 160.dp)
                    .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
                    .background(Ivory)
                    .padding(start = 12.dp), // Simulation of border-l
                contentAlignment = Alignment.Center
            ) {
                // Background color for the border-l effect
                Box(modifier = Modifier.fillMaxHeight().width(12.dp).background(Saffron).align(Alignment.CenterStart))
                
                Text("🌾", fontSize = 48.sp)
                Text(
                    "₹",
                    modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                    color = Saffron,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                "Grama-Khata",
                color = Ivory,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                "Digital Credit Book",
                color = Ivory.copy(alpha = 0.7f),
                fontStyle = FontStyle.Italic,
                fontSize = 18.sp
            )
        }
    }
}
