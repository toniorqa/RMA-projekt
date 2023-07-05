package com.example.tonikarimovicapp

import android.Manifest
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tonikarimovicapp.common.SnackbarManager
import com.example.tonikarimovicapp.ui.screens.call.VideoCallScreen
import com.example.tonikarimovicapp.ui.screens.login.LoginScreen
import com.example.tonikarimovicapp.ui.screens.splash.SplashScreen
import com.example.tonikarimovicapp.ui.screens.stage.StageScreen
import com.example.tonikarimovicapp.ui.screens.users.UsersScreen
import com.example.tonikarimovicapp.ui.theme.ToniKarimovicAppTheme
import com.example.tonikarimovicapp.webrtc.client.SignalingClient
import com.example.tonikarimovicapp.webrtc.peer.StreamPeerConnectionFactory
import com.example.tonikarimovicapp.webrtc.sessions.LocalWebRtcSessionManager
import dagger.hilt.android.AndroidEntryPoint
import com.example.tonikarimovicapp.webrtc.sessions.WebRtcSessionManagerImpl
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val sessionManager = WebRtcSessionManagerImpl(
        context = this,
        signalingClient = SignalingClient(),
        peerConnectionFactory = StreamPeerConnectionFactory(this)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO), 0)

        setContent {
            ToniKarimovicAppTheme {

                CompositionLocalProvider(LocalWebRtcSessionManager provides sessionManager) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        val appState = rememberAppState()
                        Scaffold(
                            snackbarHost = {
                                SnackbarHost(
                                    hostState = it,
                                    modifier = Modifier.padding(8.dp),
                                    snackbar = { snackbarData ->
                                        Snackbar(
                                            snackbarData,
                                            contentColor = MaterialTheme.colors.onPrimary
                                        )
                                    }
                                )
                            },
                            scaffoldState = appState.scaffoldState
                        ) { innerPaddingModifier ->

                            NavHost(
                                navController = appState.navController,
                                startDestination = SPLASH_SCREEN,
                                modifier = Modifier.padding(innerPaddingModifier)
                            ) {
                                appGraph(appState, sessionManager)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        sessionManager.disconnect()
        super.onDestroy()
    }

    @Composable
    fun rememberAppState(
        scaffoldState: ScaffoldState = rememberScaffoldState(),
        navController: NavHostController = rememberNavController(),
        snackbarManager: SnackbarManager = SnackbarManager,
        resources: Resources = resources(),
        coroutineScope: CoroutineScope = rememberCoroutineScope()
    ) =
        remember(scaffoldState, navController, snackbarManager, resources, coroutineScope) {
            AppState(scaffoldState, navController, snackbarManager, resources, coroutineScope)
        }


    @Composable
    @ReadOnlyComposable
    fun resources(): Resources {
        LocalConfiguration.current
        return LocalContext.current.resources
    }

    @OptIn(ExperimentalMaterialApi::class)
    fun NavGraphBuilder.appGraph(appState: AppState, sessionManager: WebRtcSessionManagerImpl) {
        composable(SPLASH_SCREEN) {
            SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
        }

        composable(LOGIN_SCREEN) {
            LoginScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
        }

//        composable(SIGN_UP_SCREEN) {
//            SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
//        }

        composable(USERS_SCREEN) {
            UsersScreen(
                openScreen = { route -> appState.navigate(route) },
                restartApp = { route -> appState.clearAndNavigate(route) }
            )
        }

        composable(CALL_SCREEN) {
            val state by sessionManager.signalingClient.sessionStateFlow.collectAsState()
            var onCallScreen by remember { mutableStateOf(false) }

            if (!onCallScreen) {
                StageScreen(state = state) { onCallScreen = true }
            } else {
                VideoCallScreen()
            }
        }

//        composable(
//            route = "$EDIT_TASK_SCREEN$TASK_ID_ARG",
//            arguments = listOf(navArgument(TASK_ID) { defaultValue = TASK_DEFAULT_ID })
//        ) {
//            EditTaskScreen(
//                popUpScreen = { appState.popUp() },
//                taskId = it.arguments?.getString(TASK_ID) ?: TASK_DEFAULT_ID
//            )
//        }
    }

}