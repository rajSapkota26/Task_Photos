package com.example.photos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.photos.feature.static_image_pick.StaticImageViewModel
import com.example.photos.navGraph.NavGraphNew
import com.example.photos.navGraph.Screen
import com.example.photos.ui.theme.PhotosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotosTheme {
                val viewModel = ViewModelProvider(this)[StaticImageViewModel::class.java]
                // A surface container using the 'background' color from the theme
               val navController = rememberNavController()
                NavGraphNew(
                    navController = navController,
                    startDestination = Screen.Home.route,
                )
            }
        }
    }
}




