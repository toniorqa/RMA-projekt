
package com.example.tonikarimovicapp.ui.screens.splash

import androidx.compose.runtime.mutableStateOf
import com.example.tonikarimovicapp.AppViewModel
import com.example.tonikarimovicapp.LOGIN_SCREEN
import com.example.tonikarimovicapp.SPLASH_SCREEN
import com.example.tonikarimovicapp.USERS_SCREEN
import com.example.tonikarimovicapp.services.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  private val accountService: AccountService,
) : AppViewModel() {
  val showError = mutableStateOf(false)

  fun onAppStart(openAndPopUp: (String, String) -> Unit) {

    showError.value = false
    openAndPopUp(LOGIN_SCREEN, SPLASH_SCREEN)
  }
//
//  private fun createAnonymousAccount(openAndPopUp: (String, String) -> Unit) {
//    launchCatching(snackbar = false) {
//      try {
//        accountService.createAnonymousAccount()
//      } catch (ex: FirebaseAuthException) {
//        showError.value = true
//        throw ex
//      }
//      openAndPopUp(USERS_SCREEN, SPLASH_SCREEN)
//    }
//  }
}
