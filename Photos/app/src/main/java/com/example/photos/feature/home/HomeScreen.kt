package com.example.photos.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.photos.component.FilledButton
import com.example.photos.component.TopBar
import com.example.photos.navGraph.Screen

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopBar("Image Picker")
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Please Choose any one of below option")
            FilledButton(
                label = "Static Page",
            ) {
                navController.navigate(Screen.StaticImage.route)
            }
            FilledButton(
                label = "Dynamic Page",
            ) {
                navController.navigate(Screen.DynamicImage.route)
            }


        }
    }
}