package com.denisyordanp.truckticketapp.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DateButton(
    title: String,
    value: String,
    onClicked: () -> Unit
) {
    Column {
        Text(text = title)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClicked,
            content = {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = value
                )
            },
            shape = RoundedCornerShape(4.dp)
        )
    }
}