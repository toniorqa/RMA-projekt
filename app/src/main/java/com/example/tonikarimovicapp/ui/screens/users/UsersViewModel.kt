/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.example.tonikarimovicapp.ui.screens.users

import com.example.tonikarimovicapp.AppViewModel
import com.example.tonikarimovicapp.CALL_SCREEN
import com.example.tonikarimovicapp.SPLASH_SCREEN
import com.example.tonikarimovicapp.models.User
import com.example.tonikarimovicapp.services.AccountService
import com.example.tonikarimovicapp.services.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val storageService: StorageService,
    private val accountService: AccountService
) : AppViewModel() {

    val users = storageService.users

    fun onUserActionClick(openScreen: (String) -> Unit, userItem: User) {
        openScreen(CALL_SCREEN)
    }

    fun onLogoutClicked(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.signOut()
            restartApp(SPLASH_SCREEN)
        }
    }

//  fun onTaskCheckChange(user: User) {
////    launchCatching { storageService.update(user.copy(completed = !user.completed)) }
//  }
//
//  fun onAddClick(openScreen: (String) -> Unit) = openScreen(EDIT_TASK_SCREEN)
//
//  fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)
//
//  fun onTaskActionClick(openScreen: (String) -> Unit, task: Task, action: String) {
//    when (TaskActionOption.getByTitle(action)) {
//      TaskActionOption.EditTask -> openScreen("$EDIT_TASK_SCREEN?$TASK_ID={${task.id}}")
//      TaskActionOption.ToggleFlag -> onFlagTaskClick(task)
//      TaskActionOption.DeleteTask -> onDeleteTaskClick(task)
//    }
//  }
//
//  private fun onFlagTaskClick(task: Task) {
//    launchCatching { storageService.update(task.copy(flag = !task.flag)) }
//  }
//
//  private fun onDeleteTaskClick(task: Task) {
//    launchCatching { storageService.delete(task.id) }
//  }
}
