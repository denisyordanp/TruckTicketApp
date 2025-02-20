@file:OptIn(ExperimentalMaterial3Api::class)

package com.denisyordanp.truckticketapp.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.denisyordanp.truckticketapp.common.extension.pairOf
import com.denisyordanp.truckticketapp.util.LaunchedEffectKeyed
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    shouldShowPicker: MutableState<Boolean>,
    selectedDate: Long,
    onConfirm: (selectedDate: Long) -> Unit
) {
    var datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate
    )

    BasePickerDialog(
        shouldShowPicker = shouldShowPicker,
        onConfirm = {
            datePickerState.selectedDateMillis?.let { onConfirm(it) }
        },
        content = {
            LaunchedEffectKeyed(datePickerState) {
                if (it.selectedDateMillis != selectedDate) {
                    datePickerState = DatePickerState(
                        locale = Locale.getDefault(),
                        initialSelectedDateMillis = selectedDate
                    )
                }
            }

            DatePicker(state = datePickerState)
        }
    )
}

@Composable
fun TimePickerDialog(
    shouldShowPicker: MutableState<Boolean>,
    selectedTime: Pair<Int, Int>,
    onConfirm: (selectedTime: Pair<Int, Int>) -> Unit
) {
    var timePickerState = rememberTimePickerState(
        initialHour = selectedTime.first,
        initialMinute = selectedTime.second,
        is24Hour = true
    )

    BasePickerDialog(
        shouldShowPicker = shouldShowPicker,
        onConfirm = {
            onConfirm(pairOf(timePickerState.hour, timePickerState.minute))
        },
        content = {
            LaunchedEffectKeyed(timePickerState) {
                if (it.hour != selectedTime.first || it.minute != selectedTime.second) {
                    timePickerState = TimePickerState(selectedTime.first, selectedTime.second, true)
                }
            }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Select Time")
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    TimePicker(state = timePickerState)
                }
            }
        }
    )
}

@Composable
private fun BasePickerDialog(
    shouldShowPicker: MutableState<Boolean>,
    onConfirm: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    if (shouldShowPicker.value) {
        DatePickerDialog(
            onDismissRequest = {
                shouldShowPicker.value = false
            },
            confirmButton = {
                OutlinedButton(
                    onClick = {
                        onConfirm()
                        shouldShowPicker.value = false
                    }
                ) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { shouldShowPicker.value = false }
                ) {
                    Text(text = "Cancel")
                }
            },
            content = content
        )
    }
}