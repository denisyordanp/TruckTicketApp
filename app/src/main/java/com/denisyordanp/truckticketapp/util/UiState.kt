package com.denisyordanp.truckticketapp.util

data class UiState(
    val error: Exception? = null,
    val status: UiStatus = UiStatus.INITIAL
) {
    companion object {
        fun success() = UiState(status = UiStatus.SUCCESS)
        fun error(error: Exception) = UiState(error = error, status = UiStatus.ERROR)
        fun loading() = UiState(status = UiStatus.LOADING)
    }
}

enum class UiStatus {
    SUCCESS, LOADING, INITIAL, ERROR
}