package com.denisyordanp.truckticketapp.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StickyBottom(
    modifier: Modifier = Modifier,
    stickyBottomContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        content()

        Surface(
            modifier = Modifier.align(BottomCenter).padding(bottom = 16.dp)
        ) {
            stickyBottomContent()
        }
    }
}