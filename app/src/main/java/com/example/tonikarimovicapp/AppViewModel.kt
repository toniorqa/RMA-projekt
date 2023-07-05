package com.example.tonikarimovicapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tonikarimovicapp.common.SnackbarManager
import com.example.tonikarimovicapp.common.SnackbarMessage.Companion.toSnackbarMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class AppViewModel(
//    private val logService: LogService
): ViewModel() {
    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    SnackbarManager.showMessage(throwable.toSnackbarMessage())
                }
//                logService.logNonFatalCrash(throwable)
            },
            block = block
        )

}