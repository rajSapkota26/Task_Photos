package com.example.photos.feature.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.photos.R
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
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
        Card(
            modifier = Modifier
                .fillMaxWidth().padding(4.dp)
                .border( 1.dp,
                  Color.Green,
                    RoundedCornerShape(4.dp)
                ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize().padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.description),
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                FilledButton(
                    label = "Proceed",
                ) {
                    navController.navigate(Screen.ImageViewerScreen.route)
                }


            }
        }
        }
    }
}