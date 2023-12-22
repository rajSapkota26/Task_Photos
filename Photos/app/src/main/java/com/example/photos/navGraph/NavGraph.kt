package com.example.photos.navGraph

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.photos.feature.image_pick.ImageScreen
import com.example.photos.feature.image_pick.ImageViewModel
import com.example.photos.feature.home.HomeScreen


@Composable
fun NavGraphNew(
    navController: NavHostController,
    startDestination: String,
) {
    val applicationContext = LocalContext.current.applicationContext as Application
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Screen.Home.route)
        {
            HomeScreen(navController)

        }

        composable(Screen.ImageViewerScreen.route)
        {
            val viewModel = ViewModelProvider.AndroidViewModelFactory(applicationContext)
                .create(ImageViewModel::class.java)
            ImageScreen(
                navController = navController,
                viewModel = viewModel,
                onEvent = viewModel::onEvent
            )

        }

    }

}



