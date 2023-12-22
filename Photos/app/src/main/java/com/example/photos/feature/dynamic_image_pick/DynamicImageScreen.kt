package com.example.photos.feature.dynamic_image_pick

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.photos.component.FilledButton
import com.example.photos.component.OutlinedTextFieldCompo
import com.example.photos.component.TopBar
import com.example.photos.ui.theme.PhotosTheme
import com.example.photos.utils.PermissionChecker

@Composable
fun DynamicImageScreen(
    onEvent: (DynamicImageEvent) -> Unit,
    navController: NavHostController,
    viewModel: DynamicImageViewModel
) {
    val context = LocalContext.current
    InitDependency(context = context)
    ScaffoldScreen(context = context, onEvent = { onEvent(it) }, viewModel = viewModel)
}

@Composable
fun ScaffoldScreen(
    context: Context,
    viewModel: DynamicImageViewModel,
    onEvent: (DynamicImageEvent) -> Unit
) {
    val imagePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetMultipleContents(),
            onResult = { uri ->
                if (uri.size == 2) {
                    onEvent(DynamicImageEvent.EventOnImagePick(uri))
                } else {
                    Toast.makeText(context, "Please Select Two Image at a time", Toast.LENGTH_LONG)
                        .show()
                }
            })
    Scaffold(
        topBar = {
            TopBar("Image Picker")
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(it)
        ) {
            viewModel.imageList?.let { images ->
                Box(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth(1f)
                        .fillMaxHeight(0.8f)
                        .align(Alignment.TopCenter)
                ) {
                    Column(modifier = Modifier) {
                        ImageList(images, viewModel.size, viewModel.indexes!!)

                    }
                }
            }
            Box(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(1f)
                    .fillMaxHeight(1f)
                    .align(Alignment.BottomCenter)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextFieldCompo(
                        placeholderText = "Type Size of list",
                        value = "${viewModel.size}",
                        imageVector = Icons.Filled.Person,
                        onValueChanged = {
                            if (it.isDigitsOnly()) {
                                onEvent(DynamicImageEvent.EventOnSizeChanged(it))

                            } else {
                                Toast.makeText(
                                    context,
                                    "Please Select Only Number",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                        },
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number,

                        )
                    FilledButton(
                        label = "Select Image",
                    ) {
                        if (viewModel.size.isNullOrEmpty()) {
                            Toast.makeText(context, "Please add  size of list", Toast.LENGTH_LONG)
                                .show()
                        } else {
                            imagePickerLauncher.launch("image/*")

                        }
                    }
                }
            }


        }
    }


}

@Composable
fun ImageList(pictureList: List<Uri>, sizes: String, indexes: Set<Int>) {
//    LazyVerticalGrid(columns = GridCells.Fixed(5)) {
//        items(pictureList.size) { index ->
//            val uri = pictureList[index]
//            ImageItem(uri, index)
//
//        }
//    }
    val totalItems = pictureList.size
    var count = 0
    var x = 0
    val size = sizes.toInt()
    val itemIndex = indexes
    for (i in 0 until size) {
        Row(
            Modifier
                .horizontalScroll(rememberScrollState())
                .padding(4.dp)
        ) {
            // show images
            for (k in 0 until i + 1) {
                if (count in itemIndex && count == x) {
                    com.example.photos.feature.static_image_pick.ImageItem(
                        count = count,
                        uri = pictureList[count],
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                if (count !in itemIndex && count == x) {
                    com.example.photos.feature.static_image_pick.ImageItem(
                        count = count,
                        uri = pictureList[count],
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Log.d("COUNT", count.toString())
                if (count < totalItems - 1) {
                    count++
                }
                x++
            }

        }

    }


}


@Composable
fun InitDependency(context: Context) {
    LaunchedEffect(Unit) {
        PermissionChecker.askForReadExternalStoragePermissions(context)
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PhotosTheme {

    }
}