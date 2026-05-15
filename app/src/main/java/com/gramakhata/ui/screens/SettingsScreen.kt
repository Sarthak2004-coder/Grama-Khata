package com.gramakhata.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.TextRange
import androidx.compose.material3.LocalTextStyle
import com.gramakhata.ui.theme.*
import com.gramakhata.viewmodel.GramaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: GramaViewModel) {
    val settings by viewModel.settings.collectAsState(initial = null)

    Column(modifier = Modifier.fillMaxSize().background(Ivory).padding(24.dp)) {
        Text("Settings", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Forest)
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text("SHOP PROFILE", color = Forest.copy(alpha = 0.4f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                SettingsInput(
                    label = "Shop Name",
                    value = settings?.shopName ?: "",
                    onValueChange = { newName ->
                        settings?.let { viewModel.updateSettings(it.copy(shopName = newName)) }
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = null, tint = Saffron) }
                )
                Divider(color = Color.Black.copy(alpha = 0.03f))
                SettingsInput(
                    label = "Owner Name",
                    value = settings?.ownerName ?: "",
                    onValueChange = { newName ->
                        settings?.let { viewModel.updateSettings(it.copy(ownerName = newName)) }
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = null, tint = Forest) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text("DANGER ZONE", color = Forest.copy(alpha = 0.4f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth().background(Color.Transparent),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(48.dp).background(DebitRed.copy(alpha = 0.1f), RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Delete, contentDescription = null, tint = DebitRed)
                }
                Spacer(modifier = Modifier.width(16.dp))
                TextButton(onClick = { viewModel.clearData() }) {
                    Text("Clear All Data", fontWeight = FontWeight.Bold, color = DebitRed)
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("GRAMA-KHATA V1.0.0", color = Forest.copy(alpha = 0.3f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text("Empowering village commerce with AI", color = Forest.copy(alpha = 0.2f), fontSize = 10.sp, fontStyle = FontStyle.Italic)
        }
        
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun SettingsInput(label: String, value: String, onValueChange: (String) -> Unit, icon: @Composable () -> Unit) {
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(text = value, selection = TextRange(value.length)))
    }

    // Update local state when value from parent changes externally
    LaunchedEffect(value) {
        if (value != textFieldValue.text) {
            textFieldValue = textFieldValue.copy(
                text = value,
                selection = TextRange(value.length)
            )
        }
    }

    Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(48.dp).background(Forest.copy(alpha = 0.05f), RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) {
            icon()
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(label.uppercase(), color = Forest.copy(alpha = 0.4f), fontSize = 10.sp, fontWeight = FontWeight.Bold)
            BasicTextField(
                value = textFieldValue,
                onValueChange = { newFieldValue ->
                    textFieldValue = newFieldValue
                    if (newFieldValue.text != value) {
                        onValueChange(newFieldValue.text)
                    }
                },
                textStyle = LocalTextStyle.current.copy(fontWeight = FontWeight.Bold, color = Forest, fontSize = 16.sp),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
