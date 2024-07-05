package com.denisyordanp.truckticketapp.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.cache
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavBackStackEntry
import com.denisyordanp.truckticketapp.ui.main.AppNavigator
import kotlinx.coroutines.CoroutineScope

fun NavBackStackEntry.getStringArguments(key: String): String? {
    val args = arguments?.getString(key)

    return if (args.equals("#")) null else args
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

suspend fun <T> safeCallWrapper(
    call: suspend () -> T,
    onFinish: (suspend (T) -> Unit)? = null,
    onError: (suspend (Exception) -> Unit)? = null,
) {
    try {
        val result = call()
        onFinish?.invoke(result)
    } catch (e: Exception) {
        onError?.invoke(e)
    }
}

fun <T> UiState<T>.refresh() = this.copy(error = null, status = UiStatus.REFRESH)
fun <T> UiState<T>.error(exception: Exception) =
    this.copy(error = exception, status = UiStatus.ERROR)