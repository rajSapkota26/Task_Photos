package com.example.photos.feature.static_image_pick

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.photos.component.FilledButton
import com.example.photos.component.TopBar
import com.example.photos.ui.theme.PhotosTheme
import com.example.photos.utils.PermissionChecker


@Composable
fun ImageScreen(
    onEvent: (StaticImageEvent) -> Unit,
    navController: NavHostController,
    viewModel: StaticImageViewModel
) {
    val context = LocalContext.current
    InitDependency(context = context)
    ScaffoldScreen(context = context, onEvent = { onEvent(it) }, viewModel = viewModel)
}

@Composable
fun ScaffoldScreen(
    context: Context,
    viewModel: StaticImageViewModel, onEvent: (StaticImageEvent) -> Unit
) {
    val imagePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetMultipleContents(),
            onResult = { uri ->
                if (uri.size == 2) {
                    onEvent(StaticImageEvent.EventOnImagePick(uri))
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
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize(1f)
                .padding(it)
        ) {
            viewModel.imageList?.let { images ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .fillMaxHeight(0.92f)
                ) {
                    Column {
                        ImageList(
                            pictureList = images,
                            triangleSequenceIndices = viewModel.triangleSequenceIndices
                        )
                    }

                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(1f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                FilledButton(
                    label = "Select Image",
                ) {
                    imagePickerLauncher.launch("image/*")
                }
                }
            }


        }
    }


}

@Composable
fun ImageList(pictureList: List<Uri>, triangleSequenceIndices: MutableSet<Int>) {
    val totalItems = 55
    var count = 0
    var x = 0
    val size = 200
    val itemIndex = listOf(1, 3, 6, 10, 15, 21)
    for (i in 0 until size) {
        Row(
            Modifier
                .horizontalScroll(rememberScrollState())
                .padding(4.dp)) {
            // show images
            for (k in 0 until i + 1) {
                if (count in itemIndex && count == x) {
                    ImageItem(
                        count = count,
                        uri = pictureList[count],
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                if (count !in itemIndex && count == x) {
                    ImageItem(
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

//    Column {
//        var count = 0
//        val size = 10
//
//        for (i in 0 until size) {
//            Log.d("pictureList O", pictureList.size.toString())
//            Log.d("triangleSequence", triangleSequenceIndices.size.toString())
//            Log.d("COUNT O", count.toString())
//            Log.d("COUNT Size", size.toString())
//
//            LazyRow() {
//                // show images
//                for (k in 0 until i + 1) {
//
//                    //break loop to make size 50
//                    if (i == size-1 && k > 4) {
//                        break
//                    }
//                    Log.d("COUNT TWO", count.toString())
//                    if (count < 49) {
//                        item {
//                            if (count in triangleSequenceIndices) {
//                                val uri = pictureList[count]
//                                ImageItem(uri, count)
//                            } else if (count in 0..49) {
//                                val uri = pictureList[0]
//                                ImageItem(uri, count)
//
//                            }else{
//                                val uri = pictureList[1]
//                                ImageItem(uri, count)
//                            }
//
//                            count++
//
//                        }
//
//                    }
//
//
//                }
//            }
//        }
//    }


//    LazyVerticalGrid(columns = GridCells.Fixed(5)) {
//        items(pictureList.size) { index ->
//            val uri = pictureList[index]
//            ImageItem(uri, index)
//
//        }
//    }


}

@Composable
fun ImageItem(uri: Uri, count: Int) {
    Box(
        modifier = Modifier
            .size(46.dp)
            .border(1.dp, Color.Green, CircleShape)
    ) {
        AsyncImage(
            model = (uri),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .align(Alignment.Center)
        )
        Text(
            text = "$count",
            modifier = Modifier
                .align(Alignment.TopEnd),
            color = Color.Red
        )
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