package com.denisyordanp.truckticketapp.common.extension

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class HelperTest {
    @Test
    fun `tryCatchWithDefault returns result of block when no exception`() {
        val result = tryCatchWithDefault(0) { 5 }
        assertEquals(5, result)
    }

    @Test
    fun `tryCatchWithDefault returns default value when exception occurs`() {
        val result = tryCatchWithDefault(0) { throw RuntimeException("Test Exception") }
        assertEquals(0, result)
    }

    @Test
    fun `safeCallWrapper none when only call param`() =
        runTest {
            var onStartCalled = false
            var onFinishCalled = false
            var onErrorCalled = false

            safeCallWrapper(
                call = { "Success" }
            )

            assertEquals(false, onStartCalled)
            assertEquals(false, onFinishCalled)
            assertEquals(false, onErrorCalled)
        }

    @Test
    fun `safeCallWrapper calls onStart, onFinish and not onError when no exception occurs`() =
        runTest {
            var onStartCalled = false
            var onFinishCalled = false
            var onErrorCalled = false

            safeCallWrapper(
                call = { "Success" },
                onStart = { onStartCalled = true },
                onFinish = { onFinishCalled = true },
                onError = { onErrorCalled = true }
            )

            assertEquals(true, onStartCalled)
            assertEquals(true, onFinishCalled)
            assertEquals(false, onErrorCalled)
        }

    @Test
    fun `safeCallWrapper calls onStart and onError and not onFinish when exception occurs`() =
        runTest {
            var onStartCalled = false
            var onFinishCalled = false
            var onErrorCalled = false

            safeCallWrapper(
                call = { throw RuntimeException("Test Exception") },
                onStart = { onStartCalled = true },
                onFinish = { onFinishCalled = true },
                onError = { onErrorCalled = true }
            )

            assertEquals(true, onStartCalled)
            assertEquals(false, onFinishCalled)
            assertEquals(true, onErrorCalled)
        }
}