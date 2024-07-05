package com.denisyordanp.truckticketapp.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.denisyordanp.truckticketapp.util.LocalCoroutineScope
import com.denisyordanp.truckticketapp.util.LocalNavController
import com.denisyordanp.truckticketapp.util.LocalSnackBar
import com.denisyordanp.truckticketapp.ui.main.AppNavigator.Destinations.TICKET_SCREEN
import com.denisyordanp.truckticketapp.ui.screen.detail.detailRoute
import com.denisyordanp.truckticketapp.ui.screen.manage.manageRoute
import com.denisyordanp.truckticketapp.ui.screen.tickets.ticketsRoute
import com.denisyordanp.truckticketapp.ui.theme.TruckTicketAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TruckTicketAppTheme(
                // Will not handle the dark theme for now
                darkTheme = false
            ) {
                TruckTicketLocalComposition {
                    val snackBarHostState = LocalSnackBar.current
                    val navController = LocalNavController.current

                    Scaffold(
                        modifier = Modifier
                            .systemBarsPadding(),
                        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
                    ) { padding ->
                        NavHost(
                            modifier = Modifier.padding(padding),
                            navController = navController,
                            startDestination = TICKET_SCREEN.route
                        ) {
                            ticketsRoute(this)

                            manageRoute(this)

                            detailRoute(this)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun TruckTicketLocalComposition(
        content: @Composable () -> Unit
    ) {
        val snackBarHostState = remember { SnackbarHostState() }
        val navController = rememberNavController()
        val coroutineScope = rememberCoroutineScope()

        CompositionLocalProvider(
            LocalSnackBar provides snackBarHostState,
            LocalNavController provides navController,
            LocalCoroutineScope provides coroutineScope
        ) { content() }
    }
}