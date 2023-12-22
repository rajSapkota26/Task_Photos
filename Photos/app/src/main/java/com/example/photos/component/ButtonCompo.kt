package com.example.photos.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.photos.ui.theme.PhotosTheme

@Composable
fun FilledButton(label: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp),
        onClick = { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Color.Green
        )
    ) {
        Text(label)
    }
}

@Composable
fun FilledTButton(label: String, onClick: () -> Unit) {
    FilledTonalButton(
        onClick = { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Color.Green
        )
    ) {
        Text(label)
    }
}

@Composable
fun OutlinedButtonCompo(label: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Color.Green
        ),
        border = BorderStroke(1.dp, Color.Green)
    ) {
        Text(label)
    }
}

@Composable
fun ElevatedButtonCompo(label: String, onClick: () -> Unit) {
    ElevatedButton(
        onClick = { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Color.Green
        ),
    ) {
        Text(label)
    }
}

@Composable
@Preview
fun previewCurrentButtonComponent() {
    PhotosTheme {
        Column {
            FilledButton("filled button") {}
            FilledTButton("filled Tonal button") {}
            OutlinedButtonCompo("OutLine Button") {}
            ElevatedButtonCompo("Elevated Button") {}
        }

    }
}