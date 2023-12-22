package com.example.photos.navGraph

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ImageViewerScreen : Screen("ImageViewerScreen")
}
