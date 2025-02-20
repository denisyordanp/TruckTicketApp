package com.denisyordanp.truckticketapp.ui.screen.detail

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.denisyordanp.truckticketapp.R
import com.denisyordanp.truckticketapp.common.extension.orZero
import com.denisyordanp.truckticketapp.common.extension.toFormattedDateString
import com.denisyordanp.truckticketapp.common.util.DateFormat
import com.denisyordanp.truckticketapp.schema.ui.Ticket
import com.denisyordanp.truckticketapp.ui.component.StickyBottom
import com.denisyordanp.truckticketapp.ui.component.TextContentViewer
import com.denisyordanp.truckticketapp.ui.component.TopBar
import com.denisyordanp.truckticketapp.ui.main.AppNavigator
import com.denisyordanp.truckticketapp.ui.main.AppNavigator.Destinations.DETAIL_SCREEN
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

fun detailRoute(
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.apply {
        composable(
            route = DETAIL_SCREEN.route,
            arguments = AppNavigator.licenceArguments
        ) {
            val context = LocalContext.current
            val navController = LocalNavController.current
            val snackBar = LocalSnackBar.current
            val coroutineScope = LocalCoroutineScope.current

            DetailScreen(
                id = it.getLongIdArguments(LICENCE_ARGS),
                onError = {
                    coroutineScope.launch {
                        snackBar.showSnackbar(context.getString(R.string.error_no_internet_connection))
                    }
                },
                onBackClicked = {
                    navController.popBackStack()
                },
                onEditClicked = { ticket ->
                    navController.navigate(AppNavigator.toManageScreen(ticket.id.toString()))
                }
            )
        }
    }
}

@Composable
private fun DetailScreen(
    id: Long? = null,
    viewModel: DetailScreenViewModel = hiltViewModel(),
    onError: (e: Exception) -> Unit,
    onEditClicked: (ticket: Ticket) -> Unit,
    onBackClicked: () -> Unit
) {

    if (id != null) {
        LaunchedEffectOneTime {
            viewModel.loadTicket(id)
        }
    }

    val uiState = viewModel.uiState.collectAsState()
    val ticket = viewModel.ticket.collectAsState()

    LaunchedEffectKeyed(uiState.value) {
        if (it.error != null) onError(it.error)
    }

    TopBar(
        title = stringResource(R.string.detail_ticket),
        onBackPressed = onBackClicked
    ) {
        val swipeState =
            rememberSwipeRefreshState(isRefreshing = uiState.value.status == UiStatus.LOADING)

        SwipeRefresh(
            state = swipeState,
            onRefresh = { id?.let { viewModel.loadTicket(it) } }
        ) {
            StickyBottom(
                modifier = Modifier.fillMaxSize(),
                stickyBottomContent = {
                    OutlinedButton(onClick = { ticket.value?.let { onEditClicked(it) } }) {
                        Text(text = stringResource(R.string.edit_ticket))
                    }
                }
            ) {
                ticket.value?.let {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        TextContentViewer(
                            title = stringResource(R.string.licence_number),
                            value = it.licence
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        TextContentViewer(
                            title = stringResource(R.string.date),
                            value = it.dateTime.toFormattedDateString(DateFormat.DAY_MONTH_YEAR_HOUR_MINUTE)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        TextContentViewer(
                            title = stringResource(R.string.driver_name),
                            value = it.driver
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextContentViewer(
                                modifier = Modifier.weight(1f),
                                title = stringResource(R.string.inbound_weight),
                                value = it.inbound.toString()
                            )
                            it.outbound?.let { outbound ->
                                Spacer(modifier = Modifier.width(8.dp))
                                TextContentViewer(
                                    modifier = Modifier.weight(1f),
                                    title = stringResource(R.string.outbound_weight),
                                    value = outbound.toString()
                                )
                            }
                        }
                        if (it.netWeight.orZero() > 0) {
                            Spacer(modifier = Modifier.height(16.dp))
                            TextContentViewer(
                                title = stringResource(R.string.nett_weight),
                                value = it.netWeight.toString()
                            )
                        }
                    }
                }
            }
        }
    }
}