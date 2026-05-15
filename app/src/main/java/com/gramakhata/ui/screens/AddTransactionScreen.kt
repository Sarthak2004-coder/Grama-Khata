package com.gramakhata.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import com.gramakhata.ui.theme.*
import com.gramakhata.viewmodel.GramaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    viewModel: GramaViewModel,
    initialName: String? = null,
    initialPhone: String? = null,
    initialType: String? = "give",
    onFinish: () -> Unit
) {
    var step by remember { mutableIntStateOf(if (initialName != null) 2 else 1) }
    var name by remember { mutableStateOf(initialName ?: "") }
    var phone by remember { mutableStateOf(initialPhone ?: "") }
    var photoUri by remember { mutableStateOf<String?>(null) }
    var amountStr by remember { mutableStateOf("0") }
    var type by remember { mutableStateOf(initialType ?: "give") }

    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onFinish, modifier = Modifier.padding(end = 8.dp)) {
                    Icon(Icons.Default.Close, contentDescription = "Cancel", tint = Forest)
                }
                Text("New Entry", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Forest)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(2) { index ->
                    val color = if (index + 1 <= step) Saffron else Color.Black.copy(alpha = 0.05f)
                    Box(modifier = Modifier.size(32.dp, 4.dp).background(color, RoundedCornerShape(2.dp)))
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        AnimatedContent(targetState = step, label = "StepAnimation") { currentStep ->
            if (currentStep == 1) {
                StepOne(
                    name = name,
                    phone = phone,
                    photoUri = photoUri,
                    onNameChange = { name = it },
                    onPhoneChange = { phone = it },
                    onPhotoChange = { photoUri = it },
                    onNext = { step = 2 }
                )
            } else {
                StepTwo(
                    amount = amountStr,
                    type = type,
                    onAmountChange = { amountStr = it },
                    onTypeChange = { type = it },
                    onSave = {
                        viewModel.addEntry(name, phone, amountStr.toLongOrNull() ?: 0L, type, photoUri)
                        onFinish()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepOne(
    name: String,
    phone: String,
    photoUri: String?,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onPhotoChange: (String?) -> Unit,
    onNext: () -> Unit
) {
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        onPhotoChange(uri?.toString())
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Photo Picker
        Box(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .background(Ivory)
                .clickable { photoPickerLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (photoUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(photoUri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(Icons.Default.Add, contentDescription = null, tint = Forest.copy(alpha = 0.4f))
            }
        }
        Text(
            "Add Photo (Optional)", 
            fontSize = 12.sp, 
            color = Forest.copy(alpha = 0.4f), 
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("CUSTOMER NAME", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Forest.copy(alpha = 0.4f))
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { if (it.all { char -> char.isLetter() || char.isWhitespace() }) onNameChange(it) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(color = Forest),
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Forest.copy(alpha = 0.2f)) },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Ivory, 
                unfocusedBorderColor = Color.Transparent,
                cursorColor = Forest
            )
        )
        Text("Alphabets only", fontSize = 10.sp, color = Forest.copy(alpha = 0.3f), modifier = Modifier.padding(start = 4.dp, top = 4.dp))

        Spacer(modifier = Modifier.height(24.dp))

        Text("PHONE NUMBER (MANDATORY)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Forest.copy(alpha = 0.4f))
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = phone,
            onValueChange = { if (it.length <= 10 && it.all { char -> char.isDigit() }) onPhoneChange(it) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(color = Forest),
            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = Forest.copy(alpha = 0.2f)) },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Ivory, 
                unfocusedBorderColor = Color.Transparent,
                cursorColor = Forest
            )
        )
        Text("10 digits only", fontSize = 10.sp, color = Forest.copy(alpha = 0.3f), modifier = Modifier.padding(start = 4.dp, top = 4.dp))

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth().height(64.dp),
            enabled = name.isNotBlank() && phone.length == 10,
            colors = ButtonDefaults.buttonColors(containerColor = Forest),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text("Continue", fontSize = 18.sp)
        }
    }
}

@Composable
fun StepTwo(
    amount: String,
    type: String,
    onAmountChange: (String) -> Unit,
    onTypeChange: (String) -> Unit,
    onSave: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Toggle
        Row(
            modifier = Modifier.fillMaxWidth().background(Ivory, RoundedCornerShape(20.dp)).padding(4.dp)
        ) {
            val giveColor = if (type == "give") Forest else Color.Transparent
            val giveTextColor = if (type == "give") Color.White else Forest.copy(alpha = 0.4f)
            Box(
                modifier = Modifier.weight(1f).height(48.dp).background(giveColor, RoundedCornerShape(16.dp)).clickable { onTypeChange("give") },
                contentAlignment = Alignment.Center
            ) {
                Text("GIVE (+)", fontWeight = FontWeight.Bold, color = giveTextColor)
            }
            
            val takeColor = if (type == "take") DebitRed else Color.Transparent
            val takeTextColor = if (type == "take") Color.White else Forest.copy(alpha = 0.4f)
            Box(
                modifier = Modifier.weight(1f).height(48.dp).background(takeColor, RoundedCornerShape(16.dp)).clickable { onTypeChange("take") },
                contentAlignment = Alignment.Center
            ) {
                Text("TAKE (-)", fontWeight = FontWeight.Bold, color = takeTextColor)
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("ENTRY AMOUNT", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Forest.copy(alpha = 0.4f))
            Text("₹$amount", fontSize = 64.sp, fontWeight = FontWeight.Bold, color = Forest)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Numpad
        val keys = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "00", "0", "BACK")
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(keys) { key ->
                Box(
                    modifier = Modifier
                        .height(64.dp)
                        .background(Ivory, RoundedCornerShape(16.dp))
                        .clickable {
                            when (key) {
                                "BACK" -> if (amount.length > 1) onAmountChange(amount.dropLast(1)) else onAmountChange("0")
                                else -> if (amount == "0") onAmountChange(key) else onAmountChange(amount + key)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (key == "BACK") {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Forest)
                    } else {
                        Text(key, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Forest)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth().height(64.dp),
            enabled = amount != "0",
            colors = ButtonDefaults.buttonColors(containerColor = if (type == "give") Forest else DebitRed),
            shape = RoundedCornerShape(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Save Entry", fontSize = 18.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.CheckCircle, contentDescription = null)
            }
        }
    }
}
