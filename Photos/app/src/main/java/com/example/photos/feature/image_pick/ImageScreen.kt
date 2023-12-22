package com.example.photos.feature.image_pick

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.photos.component.FilledButton
import com.example.photos.component.OutlinedTextFieldCompo
import com.example.photos.component.TopBar
import com.example.photos.ui.theme.PhotosTheme
import com.example.photos.utils.PermissionChecker

@Composable
fun ImageScreen(
    onEvent: (ImageEvent) -> Unit,
    navController: NavHostController,
    viewModel: ImageViewModel
) {
    val context = LocalContext.current
    InitDependency(context = context)
    ScaffoldScreen(context = context, onEvent = { onEvent(it) }, viewModel = viewModel)
}

@Composable
fun ScaffoldScreen(
    context: Context,
    viewModel: ImageViewModel,
    onEvent: (ImageEvent) -> Unit
) {

    Scaffold(
        topBar = {
            TopBar("Image Picker")
        },
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxHeight(1f)
                .fillMaxWidth(1f)
                .padding(it)
        ) {
            val (result, form) = createRefs()
            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(0.75f)
                    .constrainAs(result) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
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
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(0.25f)
                    .constrainAs(form) {
                        top.linkTo(result.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                if (viewModel.imageList != null) {
                    FilledButton(
                        label = "Clear ",
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp).align(Alignment.BottomCenter),
                    ) {
                        onEvent(ImageEvent.EventOnClearSize)
                    }
                }
                else {
                    Box(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxWidth(1f)
                            .fillMaxHeight(1f)
                            .align(Alignment.BottomCenter)
                    ) {
                        FormScreen(context = context, onEvent = { onEvent(it) }, viewModel = viewModel)

                    }
                }

            }

        }

    }


}

@Composable
fun FormScreen(context: Context, onEvent: (ImageEvent) -> Unit, viewModel: ImageViewModel) {
    val imagePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetMultipleContents(),
            onResult = { uri ->
                if (uri.size == 2) {
                    onEvent(ImageEvent.EventOnImagePick(uri))
                } else {
                    Toast.makeText(context, "Please Select Two Image at a time", Toast.LENGTH_LONG)
                        .show()
                }
            })
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                1.dp,
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
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add Number of image List Size",
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextFieldCompo(
                placeholderText = "Type Size of list",
                value = "${viewModel.size}",
                imageVector = Icons.Filled.AddCircle,
                onValueChanged = {
                    if (it.isDigitsOnly()) {
                        onEvent(ImageEvent.EventOnSizeChanged(it))

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
                    onEvent(ImageEvent.EventOnSizeChanged("50"))
                } else {
                    imagePickerLauncher.launch("image/*")

                }
            }
        }
    }


}

@Composable
fun ImageList(pictureList: List<Uri>, sizes: String, indexes: Set<Int>) {

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


}

@Composable
fun ImageItem(count: Int, uri: Uri) {

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