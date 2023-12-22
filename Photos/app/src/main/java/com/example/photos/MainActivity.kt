package com.example.photos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.photos.navGraph.NavGraphNew
import com.example.photos.navGraph.Screen
import com.example.photos.ui.theme.PhotosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotosTheme {
               val navController = rememberNavController()
                NavGraphNew(
                    navController = navController,
                    startDestination = Screen.Home.route,
                )
            }
        }
    }
}




