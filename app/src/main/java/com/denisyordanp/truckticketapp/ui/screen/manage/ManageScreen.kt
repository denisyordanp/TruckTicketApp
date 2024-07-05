package com.denisyordanp.truckticketapp.ui.screen.manage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.denisyordanp.truckticketapp.R
import com.denisyordanp.truckticketapp.common.extension.dateFormatToHour
import com.denisyordanp.truckticketapp.common.extension.dateFormatToMinute
import com.denisyordanp.truckticketapp.common.extension.orZero
import com.denisyordanp.truckticketapp.common.extension.safeToLong
import com.denisyordanp.truckticketapp.common.extension.toFormattedDateString
import com.denisyordanp.truckticketapp.common.util.DateFormat
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

fun manageRoute(
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.apply {
        composable(
            route = MANAGE_SCREEN.route,
            arguments = AppNavigator.licenceArguments
        ) {
            val context = LocalContext.current
            val navController = LocalNavController.current
            val snackBar = LocalSnackBar.current
            val coroutineScope = LocalCoroutineScope.current

            ManageScreen(
                licence = it.getStringArguments(LICENCE_ARGS),
                onError = {
                    coroutineScope.launch {
                        snackBar.showSnackbar(context.getString(R.string.error_please_try_again_later))
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
    licence?.let {
        LaunchedEffectOneTime {
            viewModel.setLicence(it)
        }
    }

    val ticket = viewModel.ticket.collectAsState()

    val shouldShowDatePicker = remember { mutableStateOf(false) }
    val shouldShowTimePicker = remember { mutableStateOf(false) }

    val currentDateTime = Calendar.getInstance()
    var selectedDate by remember(ticket.value?.dateTime) { mutableLongStateOf(ticket.value?.dateTime ?: currentDateTime.timeInMillis) }
    var selectedTime by remember(ticket.value?.dateTime) { mutableStateOf(
        Pair(
            first = (ticket.value?.dateTime ?: currentDateTime.timeInMillis).dateFormatToHour(),
            second = (ticket.value?.dateTime ?: currentDateTime.timeInMillis).dateFormatToMinute(),
        )
    ) }

    val title = stringResource(if (licence == null) R.string.add_new_ticket else R.string.edit_ticket)
    TopBar(
        title = title,
        onBackPressed = onBackClicked
    ) {
        var licenceNumberText by remember(ticket.value?.licence) { mutableStateOf(ticket.value?.licence.orEmpty()) }
        var driverNameText by remember(ticket.value?.driver) { mutableStateOf(ticket.value?.driver.orEmpty()) }
        var inboundText by remember(ticket.value?.inbound) { mutableLongStateOf(ticket.value?.inbound.orZero()) }
        var outboundText by remember(ticket.value?.outbound) { mutableLongStateOf(ticket.value?.outbound.orZero()) }

        StickyBottom(
            modifier = Modifier.fillMaxSize(),
            stickyBottomContent = {
                OutlinedButton(onClick = {
                    if (licence == null) {
                        viewModel.add(
                            Ticket.newTicket(
                                licence = licenceNumberText,
                                driver = driverNameText,
                                inbound = inboundText,
                                dateTime = selectedDate
                            )
                        )
                    } else {
                        viewModel.update(
                            Ticket.editTicket(
                                licence = licenceNumberText,
                                driver = driverNameText,
                                inbound = inboundText,
                                outbound = outboundText,
                                dateTime = selectedDate
                            )
                        )
                    }

                    onBackClicked()
                }) {
                    Text(text = stringResource(R.string.submit))
                }
            },
            content = {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedButton(
                            onClick = {
                                shouldShowDatePicker.value = true
                            },
                            content = {
                                Text(text = selectedDate.toFormattedDateString(DateFormat.DAY_MONTH_YEAR))
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

                    OutlinedTextField(modifier = Modifier.fillMaxWidth(), enabled = ticket.value == null , label = {
                        Text(text = stringResource(R.string.licence_number))
                    }, value = licenceNumberText, onValueChange = { licenceNumberText = it })

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(modifier = Modifier.fillMaxWidth(), label = {
                        Text(text = stringResource(R.string.driver_name))
                    }, value = driverNameText, onValueChange = { driverNameText = it })

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(modifier = Modifier.weight(1f), label = {
                            Text(text = stringResource(R.string.inbound_weight))
                        }, value = inboundText.toString(), onValueChange = { inboundText = it.safeToLong() }, suffix = {
                            Text(text = stringResource(R.string.ton))
                        })

                        licence?.let {
                            Spacer(modifier = Modifier.width(8.dp))

                            OutlinedTextField(
                                modifier = Modifier.weight(1f),
                                label = {
                                    Text(text = stringResource(R.string.outbound_weight))
                                },
                                value = outboundText.toString(),
                                onValueChange = { outboundText = it.safeToLong() },
                                suffix = {
                                    Text(text = stringResource(R.string.ton))
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
        selectedTime = selectedTime,
        onConfirm = {
            selectedTime = it
        }
    )
}