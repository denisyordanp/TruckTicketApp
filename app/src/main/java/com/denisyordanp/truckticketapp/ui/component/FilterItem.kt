package com.denisyordanp.truckticketapp.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilterItem(
    title: String,
    isSelected: Boolean,
    onItemClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable(onClick = onItemClick).padding(end = 8.dp),
        shape = CircleShape,
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
        border = if (isSelected) null else BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground)
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = title
        )
    }
}