package com.example.photos.navGraph

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object StaticImage : Screen("static_image")
    object DynamicImage : Screen("dynamic_image")
}
