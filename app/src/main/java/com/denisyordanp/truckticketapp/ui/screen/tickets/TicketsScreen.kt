package com.denisyordanp.truckticketapp.ui.screen.tickets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.denisyordanp.truckticketapp.schema.ui.Ticket
import com.denisyordanp.truckticketapp.ui.component.SortItem
import com.denisyordanp.truckticketapp.ui.component.StickyBottom
import com.denisyordanp.truckticketapp.ui.component.TopBar
import com.denisyordanp.truckticketapp.ui.main.AppNavigator
import com.denisyordanp.truckticketapp.ui.main.AppNavigator.Destinations.TICKET_SCREEN
import com.denisyordanp.truckticketapp.util.LocalCoroutineScope
import com.denisyordanp.truckticketapp.util.LocalNavController
import com.denisyordanp.truckticketapp.util.LocalSnackBar
import kotlinx.coroutines.launch

fun ticketsRoute(
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.apply {
        composable(
            route = TICKET_SCREEN.route,
        ) {
            val navController = LocalNavController.current
            val snackBar = LocalSnackBar.current
            val coroutineScope = LocalCoroutineScope.current

            TicketsScreen(
                onItemClicked = {
                    navController.navigate(AppNavigator.toDetailScreen(it.licence))
                },
                onAddButtonClicked = {
                    navController.navigate(AppNavigator.toManageScreen())
                },
                onError = {
                    coroutineScope.launch {
                        snackBar.showSnackbar("Error")
                    }
                }
            )
        }
    }
}

@Composable
private fun TicketsScreen(
    viewModel: TicketsScreenViewModel = hiltViewModel(),
    onError: (e: Exception) -> Unit,
    onItemClicked: (ticket: Ticket) -> Unit,
    onAddButtonClicked: () -> Unit,
) {
    val shouldShowSortModal = remember { mutableStateOf(false) }
    val currentSort = viewModel.ticketSort.collectAsState()

    TopBar(
        title = "Tickets",
        rightButtonIcon = Icons.Default.MoreVert,
        onRightButtonPressed = {
            shouldShowSortModal.value = !shouldShowSortModal.value
        }
    ) {
        StickyBottom(
            modifier = Modifier.fillMaxSize(),
            stickyBottomContent = {
                OutlinedButton(onClick = onAddButtonClicked) {
                    Text(text = "Add Ticket")
                }
            },
            content = {
                val tickets = viewModel.tickets.collectAsState()

                LazyColumn {
                    items(tickets.value) {
                        Text(text = it.driver)
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }
            }
        )
    }

    SortOptionModal(
        shouldShow = shouldShowSortModal,
        currentSort = currentSort.value,
        sortOptions = TicketSort.values(),
        onSelectSort = {
            viewModel.selectSort(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SortOptionModal(
    shouldShow: MutableState<Boolean>,
    currentSort: TicketSort,
    sortOptions: Array<TicketSort>,
    onSelectSort: (sort: TicketSort) -> Unit,
) {
    if (shouldShow.value) {
        ModalBottomSheet(
            onDismissRequest = {
                shouldShow.value = false
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = CenterHorizontally
            ) {
                Text(
                    text = "Select sort ticket",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    items(sortOptions) {
                        SortItem(
                            title = it.getSortTitle(),
                            isSelected = it == currentSort,
                            onItemClicked = {
                                onSelectSort(it)
                                shouldShow.value = false
                            })
                    }
                }
            }
        }
    }
}

@Composable
private fun TicketSort.getSortTitle() = when (this) {
    TicketSort.DATE -> "by Date"
    TicketSort.DRIVER -> "by Driver Name"
    TicketSort.LICENSE -> "by Licence number"
}