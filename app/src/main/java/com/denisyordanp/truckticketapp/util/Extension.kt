package com.denisyordanp.truckticketapp.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavBackStackEntry
import kotlinx.coroutines.CoroutineScope

fun NavBackStackEntry.getLongIdArguments(key: String): Long? {
    val args = arguments?.getString(key)

    return if (args.equals("#")) null else args?.toLongOrNull()
}

@Composable
fun <T> LaunchedEffectKeyed(
    key: T,
    block: suspend CoroutineScope.(key: T) -> Unit
) {
    LaunchedEffect(key1 = key) {
        block(key)
    }
}

@Composable
fun LaunchedEffectOneTime(
    block: suspend CoroutineScope.() -> Unit
) {
    var hasRun by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        if (hasRun.not()) {
            block()
            hasRun = true
        }
    }
}