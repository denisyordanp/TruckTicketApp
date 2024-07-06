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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
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
import com.denisyordanp.truckticketapp.common.extension.capitalizeFirstChar
import com.denisyordanp.truckticketapp.common.extension.dateFormatToHour
import com.denisyordanp.truckticketapp.common.extension.dateFormatToMinute
import com.denisyordanp.truckticketapp.common.extension.isZero
import com.denisyordanp.truckticketapp.common.extension.onlyAlphanumeric
import com.denisyordanp.truckticketapp.common.extension.orZero
import com.denisyordanp.truckticketapp.common.extension.pairOf
import com.denisyordanp.truckticketapp.common.extension.acceptDigitOnly
import com.denisyordanp.truckticketapp.common.extension.toFormattedDateString
import com.denisyordanp.truckticketapp.common.util.DateFormat
import com.denisyordanp.truckticketapp.schema.ui.Ticket
import com.denisyordanp.truckticketapp.ui.component.DateButton
import com.denisyordanp.truckticketapp.ui.component.DatePickerDialog
import com.denisyordanp.truckticketapp.ui.component.StickyBottom
import com.denisyordanp.truckticketapp.ui.component.TimePickerDialog
import com.denisyordanp.truckticketapp.ui.component.TopBar
import com.denisyordanp.truckticketapp.ui.main.AppNavigator
import com.denisyordanp.truckticketapp.ui.main.AppNavigator.Destinations.MANAGE_SCREEN
import com.denisyordanp.truckticketapp.ui.main.AppNavigator.LICENCE_ARGS
import com.denisyordanp.truckticketapp.util.LaunchedEffectKeyed
import com.denisyordanp.truckticketapp.util.LaunchedEffectOneTime
import com.denisyordanp.truckticketapp.util.LocalCoroutineScope
import com.denisyordanp.truckticketapp.util.LocalNavController
import com.denisyordanp.truckticketapp.util.LocalSnackBar
import com.denisyordanp.truckticketapp.util.UiStatus
import com.denisyordanp.truckticketapp.util.getLongIdArguments
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
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
                id = it.getLongIdArguments(LICENCE_ARGS),
                onError = {
                    coroutineScope.launch {
                        snackBar.showSnackbar(context.getString(R.string.error_no_internet_connection))
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
    id: Long? = null,
    viewModel: ManageScreenViewModel = hiltViewModel(),
    onError: (e: Exception) -> Unit,
    onBackClicked: () -> Unit
) {
    if (id != null) {
        LaunchedEffectOneTime {
            viewModel.loadTicket(id)
        }
    }

    val uiState = viewModel.uiState.collectAsState()
    val ticket = viewModel.ticket.collectAsState()

    val shouldShowDatePicker = remember { mutableStateOf(false) }
    val shouldShowTimePicker = remember { mutableStateOf(false) }

    val currentDateTime = Calendar.getInstance()
    var selectedDate by remember(ticket.value?.dateTime) {
        mutableLongStateOf(
            ticket.value?.dateTime ?: currentDateTime.timeInMillis
        )
    }
    var selectedTime by remember(ticket.value?.dateTime) {
        mutableStateOf(
            pairOf(
                first = (ticket.value?.dateTime ?: currentDateTime.timeInMillis).dateFormatToHour(),
                second = (ticket.value?.dateTime
                    ?: currentDateTime.timeInMillis).dateFormatToMinute(),
            )
        )
    }

    LaunchedEffectKeyed(uiState.value) {
        if (it.error != null) onError(it.error)
    }

    val title =
        stringResource(if (id == null) R.string.add_new_ticket else R.string.edit_ticket)
    TopBar(
        title = title,
        onBackPressed = onBackClicked
    ) {
        val swipeState =
            rememberSwipeRefreshState(isRefreshing = uiState.value.status == UiStatus.LOADING)

        SwipeRefresh(
            state = swipeState,
            onRefresh = { id?.let { viewModel.loadTicket(it) } }
        ) {
            ManageContent(
                ticket = ticket.value,
                selectedDate = selectedDate,
                selectedTime = selectedTime,
                shouldShowDatePicker = shouldShowDatePicker,
                shouldShowTimePicker = shouldShowTimePicker,
                onSubmitClicked = { isAdd, ticket ->
                    if (isAdd) viewModel.add(ticket) else viewModel.update(ticket)
                },
                onBackClicked = onBackClicked
            )
        }
    }

    DatePickerDialog(
        shouldShowPicker = shouldShowDatePicker,
        selectedDate = selectedDate,
        onConfirm = {
            selectedDate = it
        }
    )

    // TODO: Fix time picker initial value
    TimePickerDialog(
        shouldShowPicker = shouldShowTimePicker,
        selectedTime = selectedTime,
        onConfirm = {
            selectedTime = it
        }
    )
}

@Composable
private fun ManageContent(
    ticket: Ticket?,
    selectedDate: Long,
    selectedTime: Pair<Int, Int>,
    shouldShowDatePicker: MutableState<Boolean>,
    shouldShowTimePicker: MutableState<Boolean>,
    onSubmitClicked: (isAdd: Boolean, ticket: Ticket) -> Unit,
    onBackClicked: () -> Unit
) {
    var licenceNumberText by remember(ticket?.licence) { mutableStateOf(ticket?.licence.orEmpty()) }
    var driverNameText by remember(ticket?.driver) { mutableStateOf(ticket?.driver.orEmpty()) }
    var inboundText by remember(ticket?.inbound) { mutableLongStateOf(ticket?.inbound.orZero()) }
    var outboundText by remember(ticket?.outbound) { mutableLongStateOf(ticket?.outbound.orZero()) }

    val isLicenceTextError by remember(licenceNumberText) { derivedStateOf { licenceNumberText.isEmpty() } }
    val isDriverTextError by remember(driverNameText) { derivedStateOf { driverNameText.isEmpty() } }
    val isInboundTextError by remember(inboundText) { derivedStateOf { inboundText.isZero() } }
    val isOutboundTextError by remember(outboundText, ticket, inboundText) {
        derivedStateOf {
            if (ticket != null) outboundText.isZero() || outboundText <= inboundText else false
        }
    }
    val isSubmitButtonEnabled by remember(
        isLicenceTextError,
        isDriverTextError,
        isInboundTextError,
        isOutboundTextError
    ) {
        derivedStateOf {
            !isLicenceTextError && !isDriverTextError && !isInboundTextError && !isOutboundTextError
        }
    }

    StickyBottom(
        modifier = Modifier.fillMaxSize(),
        stickyBottomContent = {
            OutlinedButton(
                onClick = {
                    val isAdd = ticket == null
                    val newTicket = if (isAdd) {
                        Ticket.newTicket(
                            licence = licenceNumberText,
                            driver = driverNameText,
                            inbound = inboundText,
                            dateTime = selectedDate
                        )
                    } else {
                        Ticket.existingTicket(
                            id = ticket?.id.orZero(),
                            licence = licenceNumberText,
                            driver = driverNameText,
                            inbound = inboundText,
                            outbound = outboundText,
                            dateTime = selectedDate
                        )
                    }
                    onSubmitClicked(isAdd, newTicket)
                    onBackClicked()
                },
                enabled = isSubmitButtonEnabled
            ) {
                Text(text = stringResource(R.string.submit))
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                DateButton(
                    title = stringResource(R.string.date),
                    value = selectedDate.toFormattedDateString(DateFormat.DAY_MONTH_YEAR),
                    onClicked = {
                        shouldShowDatePicker.value = true
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                DateButton(
                    title = stringResource(R.string.time),
                    value = "${selectedTime.first}:${selectedTime.second}",
                    onClicked = {
                        shouldShowTimePicker.value = true
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = ticket == null,
                    label = {
                        Text(text = stringResource(R.string.licence_number))
                    },
                    isError = isLicenceTextError,
                    value = licenceNumberText,
                    onValueChange = { licenceNumberText = it.uppercase().onlyAlphanumeric() }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = stringResource(R.string.driver_name))
                    },
                    value = driverNameText,
                    isError = isDriverTextError,
                    onValueChange = { driverNameText = it.capitalizeFirstChar() })

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        label = {
                            Text(text = stringResource(R.string.inbound_weight))
                        },
                        value = inboundText.toString(),
                        isError = isInboundTextError,
                        onValueChange = { inboundText = it.acceptDigitOnly() },
                        suffix = {
                            Text(text = stringResource(R.string.ton))
                        })

                    ticket?.let {
                        Spacer(modifier = Modifier.width(8.dp))

                        OutlinedTextField(
                            modifier = Modifier.weight(1f),
                            label = {
                                Text(text = stringResource(R.string.outbound_weight))
                            },
                            isError = isOutboundTextError,
                            value = outboundText.toString(),
                            onValueChange = { outboundText = it.acceptDigitOnly() },
                            suffix = {
                                Text(text = stringResource(R.string.ton))
                            })
                    }
                }
            }
        }
    )
}