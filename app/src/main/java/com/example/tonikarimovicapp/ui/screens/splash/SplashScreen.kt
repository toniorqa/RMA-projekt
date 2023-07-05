package com.example.tonikarimovicapp.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import com.example.tonikarimovicapp.R.string as AppText

private const val SPLASH_TIMEOUT = 1000L

@Composable
fun SplashScreen(
  openAndPopUp: (String, String) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: SplashViewModel = hiltViewModel()
) {
  Column(
    modifier =
    modifier
      .fillMaxWidth()
      .fillMaxHeight()
      .background(color = MaterialTheme.colors.background)
      .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    if (viewModel.showError.value) {
      Text(text = stringResource(AppText.something_went_wrong))

      Button(
        onClick = {
           viewModel.onAppStart(openAndPopUp)
        }
      ){
        Text(text = stringResource(id = AppText.try_again))
      }
    } else {
      CircularProgressIndicator(color = MaterialTheme.colors.onBackground)
    }
  }

  LaunchedEffect(true) {
    delay(SPLASH_TIMEOUT)
    viewModel.onAppStart(openAndPopUp)
  }
}
