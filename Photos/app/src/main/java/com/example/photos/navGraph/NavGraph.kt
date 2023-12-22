package com.example.photos.navGraph

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.photos.feature.dynamic_image_pick.DynamicImageScreen
import com.example.photos.feature.dynamic_image_pick.DynamicImageViewModel
import com.example.photos.feature.home.HomeScreen
import com.example.photos.feature.static_image_pick.ImageScreen
import com.example.photos.feature.static_image_pick.StaticImageViewModel


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
        composable(Screen.StaticImage.route)
        {
            val viewModel = ViewModelProvider.AndroidViewModelFactory(applicationContext)
                .create(StaticImageViewModel::class.java)

            ImageScreen(
                navController = navController,
                viewModel = viewModel,
                onEvent = viewModel::onEvent
            )
        }
        composable(Screen.DynamicImage.route)
        {
            val viewModel = ViewModelProvider.AndroidViewModelFactory(applicationContext)
                .create(DynamicImageViewModel::class.java)
            DynamicImageScreen(
                navController = navController,
                viewModel = viewModel,
                onEvent = viewModel::onEvent
            )

        }

    }

}



