package com.example.tonikarimovicapp.ui.screens.users

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collect
import com.example.tonikarimovicapp.R.string as AppText

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@ExperimentalMaterialApi
fun UsersScreen(
  openScreen: (String) -> Unit,
  modifier: Modifier = Modifier,
  restartApp: (String) -> Unit,
  viewModel: UsersViewModel = hiltViewModel()
) {
  Scaffold {
    val users = viewModel.users.collectAsStateWithLifecycle(emptyList())

    Column(modifier = Modifier
      .fillMaxWidth()
      .fillMaxHeight()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
      ) {
        ClickableText(
          modifier = modifier.padding(16.dp),
          text = AnnotatedString.Builder(stringResource(AppText.logout)).toAnnotatedString(),
          onClick = {viewModel.onLogoutClicked(restartApp) }
        )
      }

      LazyColumn {
        items(users.value, key = { it.id }) { userItem ->
          UserItem(
            user = userItem,
            onActionClick = { viewModel.onUserActionClick(openScreen, userItem) }
          )
        }
      }
    }
  }
}
