package com.baghdad.tudee.presentation.base

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baghdad.tudee.presentation.composable.SnackbarState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<S, E>(initialState: S) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<E>()
    val effects = _effects.asSharedFlow()

    private val _snackbarState = MutableStateFlow(SnackbarState())
    val snackbarState = _snackbarState.asStateFlow()

    protected fun <T> tryToExecute(
        function: suspend () -> T,
        onSuccess: ((T) -> Unit)? = null,
        onError: (Throwable) -> Unit,
        scope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Job = runWithErrorHandling(onError, scope, dispatcher) {
        function().let { result ->
            onSuccess?.invoke(result)
        }
    }

    protected fun <T> tryToCollect(
        function: suspend () -> Flow<T>,
        onNewValue: suspend (T) -> Unit,
        onError: (Throwable) -> Unit,
        scope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Job =
        runWithErrorHandling(onError, scope, dispatcher) {
            function().distinctUntilChanged().collectLatest {
                onNewValue(it)
            }
        }

    protected fun updateState(updater: (S) -> S) {
        _state.update(updater)
    }

    protected val currentState: S
        get() = _state.value

    protected fun showSnackbar(
        @StringRes
        messageRes: Int,
        isSuccess: Boolean,
        durationMillis: Long = 3000L,
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            _snackbarState.update {
                SnackbarState(
                    messageRes = messageRes,
                    isSuccess = isSuccess,
                    isVisible = true
                )
            }
            delay(durationMillis)
            _snackbarState.update {
                SnackbarState(
                    isVisible = false,
                )
            }
        }
    }

    protected fun emitNewEffect(effect: E) {
        viewModelScope.launch(Dispatchers.Main) {
            _effects.emit(effect)
        }
    }

    private fun runWithErrorHandling(
        onError: (Throwable) -> Unit,
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        function: suspend () -> Unit,
    ): Job {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            onError(throwable)
        }
        return scope.launch(dispatcher + coroutineExceptionHandler) {
            function()
        }
    }
}