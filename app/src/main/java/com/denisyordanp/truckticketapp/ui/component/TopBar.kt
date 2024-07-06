package com.denisyordanp.truckticketapp.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons.AutoMirrored.Default
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    title: String,
    rightButtonIcon: ImageVector? = null,
    onBackPressed: (() -> Unit)? = null,
    onRightButtonPressed: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Column {
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 16.dp)
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                onBackPressed?.let {
                    IconButton(
                        onClick = it
                    ) {
                        Icon(
                            imageVector = Default.ArrowBack,
                            contentDescription = null
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                } ?: Spacer(modifier = Modifier.width(40.dp))

                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )

                rightButtonIcon?.let {
                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = { onRightButtonPressed?.invoke() }
                    ) {
                        Icon(imageVector = it, contentDescription = null)
                    }
                } ?: Spacer(modifier = Modifier.width(40.dp))
            }
        }
        HorizontalDivider()
        content()
    }
}