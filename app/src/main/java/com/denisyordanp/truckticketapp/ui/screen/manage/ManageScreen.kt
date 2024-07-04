package com.denisyordanp.truckticketapp.ui.screen.manage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.denisyordanp.truckticketapp.schema.ui.Ticket
import com.denisyordanp.truckticketapp.ui.component.DatePickerDialog
import com.denisyordanp.truckticketapp.ui.component.StickyBottom
import com.denisyordanp.truckticketapp.ui.component.TimePickerDialog
import com.denisyordanp.truckticketapp.ui.component.TopBar
import com.denisyordanp.truckticketapp.ui.main.AppNavigator
import com.denisyordanp.truckticketapp.ui.main.AppNavigator.Destinations.MANAGE_SCREEN
import com.denisyordanp.truckticketapp.ui.main.AppNavigator.LICENCE_ARGS
import com.denisyordanp.truckticketapp.util.LaunchedEffectOneTime
import com.denisyordanp.truckticketapp.util.LocalCoroutineScope
import com.denisyordanp.truckticketapp.util.LocalNavController
import com.denisyordanp.truckticketapp.util.LocalSnackBar
import com.denisyordanp.truckticketapp.util.getStringArguments
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

fun manageRoute(
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.apply {
        composable(
            route = MANAGE_SCREEN.route,
            arguments = AppNavigator.licenceArguments
        ) {
            val navController = LocalNavController.current
            val snackBar = LocalSnackBar.current
            val coroutineScope = LocalCoroutineScope.current

            ManageScreen(
                licence = it.getStringArguments(LICENCE_ARGS),
                onError = {
                    coroutineScope.launch {
                        snackBar.showSnackbar("Error")
                    }
                },
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
private fun ManageScreen(
    licence: String? = null,
    viewModel: ManageScreenViewModel = hiltViewModel(),
    onError: (e: Exception) -> Unit,
    onBackClicked: () -> Unit
) {
    val title = if (licence == null) "Add new Ticket" else "Edit Ticket"
    val shouldShowDatePicker = remember { mutableStateOf(false) }
    val shouldShowTimePicker = remember { mutableStateOf(false) }

    val currentDateTime = Calendar.getInstance()
    var selectedDate by remember { mutableLongStateOf(currentDateTime.timeInMillis) }
    var selectedTime by remember { mutableStateOf(Pair(currentDateTime.get(Calendar.HOUR_OF_DAY), currentDateTime.get(Calendar.MINUTE))) }

    TopBar(
        title = title,
        onBackPressed = onBackClicked
    ) {
        var licenceNumberText by remember { mutableStateOf("") }
        var driverNameText by remember { mutableStateOf("") }
        var inboundText by remember { mutableStateOf("") }
        var outboundText by remember { mutableStateOf("") }

        StickyBottom(
            modifier = Modifier.fillMaxSize(),
            stickyBottomContent = {
                OutlinedButton(onClick = {
                    if (licence == null) {
                        viewModel.addTicket(
                            Ticket.newTicket(
                                licence = licenceNumberText,
                                driver = driverNameText,
                                inbound = inboundText.toLong(),
                                dateTime = selectedDate
                            )
                        )
                    } else {
                        viewModel.updateTicket(
                            Ticket(
                                licence = licenceNumberText,
                                driver = driverNameText,
                                inbound = inboundText.toLong(),
                                outbound = outboundText.toLong(),
                                netWeight = null,
                                dateTime = System.currentTimeMillis()
                            )
                        )
                    }

                }) {
                    Text(text = "Submit")
                }
            },
            content = {
                licence?.let {
                    LaunchedEffectOneTime {
                        viewModel.loadTicket(it)
                    }
                }

                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedButton(
                            onClick = {
                                shouldShowDatePicker.value = true
                            },
                            content = {
                                Text(text = selectedDate.toString())
                            },
                            shape = RoundedCornerShape(16.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        OutlinedButton(
                            onClick = {
                                shouldShowTimePicker.value = true
                            },
                            content = {
                                Text(text = "${selectedTime.first}:${selectedTime.second}")
                            },
                            shape = RoundedCornerShape(16.dp)
                        )
                    }

                    OutlinedTextField(modifier = Modifier.fillMaxWidth(), label = {
                        Text(text = "Licence number")
                    }, value = licenceNumberText, onValueChange = { licenceNumberText = it })

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(modifier = Modifier.fillMaxWidth(), label = {
                        Text(text = "Driver name")
                    }, value = driverNameText, onValueChange = { driverNameText = it })

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(modifier = Modifier.weight(1f), label = {
                            Text(text = "Inbound weight")
                        }, value = inboundText, onValueChange = { inboundText = it }, suffix = {
                            Text(text = "Ton")
                        })

                        licence?.let {
                            Spacer(modifier = Modifier.width(8.dp))

                            OutlinedTextField(
                                modifier = Modifier.weight(1f),
                                label = {
                                    Text(text = "Outbound weight")
                                },
                                value = outboundText,
                                onValueChange = { outboundText = it },
                                suffix = {
                                    Text(text = "Ton")
                                })
                        }
                    }
                }
            }
        )
    }

    DatePickerDialog(
        shouldShowPicker = shouldShowDatePicker,
        selectedDate = selectedDate,
        onConfirm = {
            selectedDate = it
        }
    )

    TimePickerDialog(
        shouldShowPicker = shouldShowTimePicker,
        selectedHour = selectedTime.first,
        selectedMinute = selectedTime.second,
        onConfirm = {
            selectedTime = it
        }
    )
}