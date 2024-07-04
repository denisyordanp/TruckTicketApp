package com.denisyordanp.truckticketapp.ui.main

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.denisyordanp.truckticketapp.common.extension.orDefault

object AppNavigator {
    const val LICENCE_ARGS = "licence"
    const val DETAIL_SCREEN_PATH = "detail"
    const val MANAGE_SCREEN_PATH = "manage_ticket_screen"

    val licenceArguments = listOf(
        navArgument(name = LICENCE_ARGS) {
            type = NavType.StringType
        }
    )

    fun toDetailScreen(licence: String) = "$DETAIL_SCREEN_PATH/$licence"
    fun toManageScreen(licence: String? = null) = "$MANAGE_SCREEN_PATH/${licence.orDefault("#")}"

    enum class Destinations(val route: String) {
        TICKET_SCREEN("ticket"),
        DETAIL_SCREEN("$DETAIL_SCREEN_PATH/{$LICENCE_ARGS}"),
        MANAGE_SCREEN("$MANAGE_SCREEN_PATH/{$LICENCE_ARGS}")
    }
}