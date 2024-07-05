package com.denisyordanp.truckticketapp.ui.screen.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.denisyordanp.truckticketapp.common.extension.toFormattedDateString
import com.denisyordanp.truckticketapp.common.util.DateFormat
import com.denisyordanp.truckticketapp.schema.ui.Ticket
import com.denisyordanp.truckticketapp.ui.component.TopBar
import com.denisyordanp.truckticketapp.ui.main.AppNavigator
import com.denisyordanp.truckticketapp.ui.main.AppNavigator.Destinations.DETAIL_SCREEN
import com.denisyordanp.truckticketapp.ui.main.AppNavigator.LICENCE_ARGS
import com.denisyordanp.truckticketapp.util.LaunchedEffectOneTime
import com.denisyordanp.truckticketapp.util.LocalCoroutineScope
import com.denisyordanp.truckticketapp.util.LocalNavController
import com.denisyordanp.truckticketapp.util.LocalSnackBar
import com.denisyordanp.truckticketapp.util.getStringArguments
import kotlinx.coroutines.launch

fun detailRoute(
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.apply {
        composable(
            route = DETAIL_SCREEN.route,
            arguments = AppNavigator.licenceArguments
        ) {
            val navController = LocalNavController.current
            val snackBar = LocalSnackBar.current
            val coroutineScope = LocalCoroutineScope.current

            DetailScreen(
                licence = it.getStringArguments(LICENCE_ARGS),
                onError = {
                    coroutineScope.launch {
                        snackBar.showSnackbar("Error")
                    }
                },
                onBackClicked = {
                    navController.popBackStack()
                },
                onEditClicked = { ticket ->
                    navController.navigate(AppNavigator.toManageScreen(ticket.licence))
                }
            )
        }
    }
}

@Composable
private fun DetailScreen(
    licence: String? = null,
    viewModel: DetailScreenViewModel = hiltViewModel(),
    onError: (e: Exception) -> Unit,
    onEditClicked: (ticket: Ticket) -> Unit,
    onBackClicked: () -> Unit
) {

    licence?.let {
        LaunchedEffectOneTime {
            viewModel.setLicence(it)
        }
    }

    TopBar(
        title = "Detail Ticket",
        onBackPressed = onBackClicked
    ) {
        val ticket = viewModel.ticket.collectAsState()

        ticket.value?.let {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = it.licence)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it.driver)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it.inbound.toString())
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it.outbound.toString())
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it.netWeight.toString())
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it.dateTime.toFormattedDateString(DateFormat.DAY_MONTH_YEAR_HOUR_MINUTE))
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedButton(onClick = { onEditClicked(it) }) {
                    Text(text = "Edit ticket")
                }
            }
        }


    }
}