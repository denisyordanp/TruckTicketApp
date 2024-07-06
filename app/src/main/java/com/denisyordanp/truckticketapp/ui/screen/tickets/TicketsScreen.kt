package com.denisyordanp.truckticketapp.ui.screen.tickets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.denisyordanp.truckticketapp.R
import com.denisyordanp.truckticketapp.common.extension.pairOf
import com.denisyordanp.truckticketapp.common.extension.toFormattedDateString
import com.denisyordanp.truckticketapp.common.util.DateFormat
import com.denisyordanp.truckticketapp.common.util.TicketParam
import com.denisyordanp.truckticketapp.common.util.TicketParam.DATE
import com.denisyordanp.truckticketapp.common.util.TicketParam.DRIVER
import com.denisyordanp.truckticketapp.common.util.TicketParam.LICENSE
import com.denisyordanp.truckticketapp.schema.ui.Ticket
import com.denisyordanp.truckticketapp.ui.component.FilterItem
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
            val context = LocalContext.current
            val navController = LocalNavController.current
            val snackBar = LocalSnackBar.current
            val coroutineScope = LocalCoroutineScope.current

            TicketsScreen(
                onItemClicked = {
                    navController.navigate(AppNavigator.toDetailScreen(it.id.toString()))
                },
                onEditClicked = {
                    navController.navigate(AppNavigator.toManageScreen(it.id.toString()))
                },
                onAddButtonClicked = {
                    navController.navigate(AppNavigator.toManageScreen())
                },
                onError = {
                    coroutineScope.launch {
                        snackBar.showSnackbar(context.getString(R.string.error_please_try_again_later))
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
    onEditClicked: (ticket: Ticket) -> Unit,
    onAddButtonClicked: () -> Unit,
) {
    val shouldShowSortModal = remember { mutableStateOf(false) }
    val currentSort = viewModel.ticketSort.collectAsState()
    val selectedFilter = viewModel.ticketFilter.collectAsState()
    val filterDate = viewModel.filterDate.collectAsState()
    val filterLicence = viewModel.filterLicence.collectAsState()
    val filterDriver = viewModel.filterDriver.collectAsState()

    TopBar(
        title = stringResource(R.string.tickets),
        rightButtonIcon = Icons.Default.MoreVert,
        onRightButtonPressed = {
            shouldShowSortModal.value = !shouldShowSortModal.value
        }
    ) {
        StickyBottom(
            modifier = Modifier.fillMaxSize(),
            stickyBottomContent = {
                OutlinedButton(onClick = onAddButtonClicked) {
                    Text(text = stringResource(R.string.add_ticket))
                }
            },
            content = {
                val tickets = viewModel.tickets.collectAsState()

                LazyColumn {
                    items(tickets.value) {
                        TicketItem(
                            item = it,
                            onItemClicked = {
                                onItemClicked(it)
                            },
                            onEditClicked = {
                                onEditClicked(it)
                            }
                        )
                    }
                }
            }
        )
    }

    SortOptionModal(
        shouldShow = shouldShowSortModal,
        currentSort = currentSort.value,
        sortOptions = TicketParam.values(),
        filterDate = filterDate.value,
        filterLicence = filterLicence.value,
        filterDriver = filterDriver.value,
        selectedFilter = selectedFilter.value,
        onSelectSort = {
            viewModel.selectSort(it)
        },
        onSelectFilter = {
            viewModel.selectFilter(it)
        },
        onClearFilter = {
            viewModel.selectFilter(null)
        }
    )
}

@Composable
fun TicketItem(
    item: Ticket,
    onItemClicked: () -> Unit,
    onEditClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClicked)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.dateTime.toFormattedDateString(DateFormat.DAY_MONTH_YEAR_HOUR_MINUTE),
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = item.licence, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = item.driver, style = MaterialTheme.typography.titleSmall)
        }

        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = onEditClicked) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = stringResource(R.string.edit)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SortOptionModal(
    shouldShow: MutableState<Boolean>,
    currentSort: TicketParam,
    sortOptions: Array<TicketParam>,
    filterDate: List<Long>,
    filterLicence: List<String>,
    filterDriver: List<String>,
    selectedFilter: Pair<TicketParam, String>?,
    onSelectSort: (sort: TicketParam) -> Unit,
    onSelectFilter: (filter: Pair<TicketParam, String>) -> Unit,
    onClearFilter: () -> Unit
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
                    .padding(8.dp)
            ) {
                SortContent(
                    currentSort = currentSort,
                    sortOptions = sortOptions,
                    onSelectSort = {
                        onSelectSort(it)
                        shouldShow.value = false
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                FilterContent(
                    filterDate = filterDate,
                    filterLicence = filterLicence,
                    filterDriver = filterDriver,
                    selectedFilter = selectedFilter,
                    onSelectFilter = {
                        if (it != selectedFilter) onSelectFilter(it) else onClearFilter()
                        shouldShow.value = false
                    }
                )
            }
        }
    }
}

@Composable
private fun SortContent(
    currentSort: TicketParam,
    sortOptions: Array<TicketParam>,
    onSelectSort: (sort: TicketParam) -> Unit
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(R.string.select_sort_ticket),
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(16.dp))

    LazyColumn {
        items(sortOptions) {
            SortItem(
                title = it.getSortTitle(),
                isSelected = it == currentSort,
                onItemClicked = {
                    onSelectSort(it)
                })
        }
    }
}

@Composable
private fun FilterContent(
    filterDate: List<Long>,
    filterLicence: List<String>,
    filterDriver: List<String>,
    selectedFilter: Pair<TicketParam, String>?,
    onSelectFilter: (filter: Pair<TicketParam, String>) -> Unit
) {
    if (filterDate.isNotEmpty() || filterLicence.isNotEmpty() || filterDriver.isNotEmpty()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.select_filter_by),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.date),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(filterDate) {
                val formattedDate = it.toFormattedDateString(DateFormat.DAY_MONTH_YEAR)
                FilterItem(
                    title = formattedDate,
                    isSelected = selectedFilter?.first.checkFilter(
                        DATE,
                        pairOf(formattedDate, selectedFilter?.second)
                    ),
                    onItemClick = {
                        onSelectFilter(pairOf(DATE, formattedDate))
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.licence_number),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(filterLicence) {
                FilterItem(
                    title = it,
                    isSelected = selectedFilter?.first.checkFilter(
                        LICENSE,
                        pairOf(it, selectedFilter?.second)
                    ),
                    onItemClick = {
                        onSelectFilter(pairOf(LICENSE, it))
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.driver_name),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(filterDriver) {
                FilterItem(
                    title = it,
                    isSelected = selectedFilter?.first.checkFilter(
                        DRIVER,
                        pairOf(it, selectedFilter?.second)
                    ),
                    onItemClick = {
                        onSelectFilter(pairOf(DRIVER, it))
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

private fun TicketParam?.checkFilter(current: TicketParam, compare: Pair<String, String?>) =
    if (this == current) compare.first.equals(compare.second, true) else false

@Composable
private fun TicketParam.getSortTitle() = when (this) {
    DATE -> stringResource(R.string.by_date)
    DRIVER -> stringResource(R.string.by_driver_name)
    LICENSE -> stringResource(R.string.by_licence_number)
}