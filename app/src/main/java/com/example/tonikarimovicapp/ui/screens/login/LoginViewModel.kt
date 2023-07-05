package com.example.tonikarimovicapp.ui.screens.login

import androidx.compose.runtime.mutableStateOf
import com.example.tonikarimovicapp.AppViewModel
import com.example.tonikarimovicapp.LOGIN_SCREEN
import com.example.tonikarimovicapp.USERS_SCREEN
import com.example.tonikarimovicapp.common.SnackbarManager
import com.example.tonikarimovicapp.common.isValidEmail
import com.example.tonikarimovicapp.services.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.tonikarimovicapp.R.string as AppText


@HiltViewModel
class LoginViewModel @Inject constructor(private val accountService: AccountService): AppViewModel() {

    var uiState = mutableStateOf(LoginState(false, "test@gmail.com", password="123456"))
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password


    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_password_error)
            return
        }

        launchCatching {
            accountService.authenticate(email, password)
            openAndPopUp(USERS_SCREEN, LOGIN_SCREEN)
        }
    }
    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }


}