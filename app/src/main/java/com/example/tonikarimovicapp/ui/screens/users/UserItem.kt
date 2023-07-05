package com.example.tonikarimovicapp.ui.screens.users

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tonikarimovicapp.models.User

@Composable
@ExperimentalMaterialApi
fun UserItem(
  user: User,
  onActionClick: () -> Unit
) {
  Card(
    backgroundColor = MaterialTheme.colors.background,
    modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp),
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth(),
    ) {

      Column(modifier = Modifier.weight(1f).clickable {
        onActionClick()
      }) {
        Text(text = user.email, style = MaterialTheme.typography.subtitle2)
      }
    }
  }
}


