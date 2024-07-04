package com.denisyordanp.truckticketapp.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SortItem(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean,
    onItemClicked: () -> Unit
) {
    Row(
        modifier = modifier.clickable(enabled = isSelected.not(), onClick = onItemClicked).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title)
        Spacer(modifier = Modifier.weight(1f))
        if (isSelected) {
            Icon(imageVector = Icons.Default.Done, contentDescription = "Selected")
        }
    }
}