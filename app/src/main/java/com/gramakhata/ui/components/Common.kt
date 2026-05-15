package com.gramakhata.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gramakhata.ui.theme.CreditGreen
import com.gramakhata.ui.theme.DebitRed
import com.gramakhata.ui.theme.Saffron

@Composable
fun GramaButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: String = "primary",
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    val containerColor = when (variant) {
        "primary" -> MaterialTheme.colorScheme.primary
        "secondary" -> Saffron
        "debit" -> DebitRed
        "credit" -> CreditGreen
        else -> Color.Transparent
    }
    
    val contentColor = if (variant == "ghost") MaterialTheme.colorScheme.primary else Color.White

    Button(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(alpha = 0.5f)
        ),
        enabled = enabled,
        content = content
    )
}
