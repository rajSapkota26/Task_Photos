package com.example.photos.feature.image_pick

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class ImageViewModel : ViewModel() {
    var size by mutableStateOf("")
    var imageList by mutableStateOf<List<Uri>?>(null)
    var indexes by mutableStateOf<Set<Int>?>(null)


    fun onEvent(event: ImageEvent) {
        when (event) {
            is ImageEvent.EventOnImagePick -> {
                val list = mutableListOf<Uri>()
                val indices = mutableSetOf<Int>()
                for (i in 1..size.toInt()) {
                    val ind = i * (i + 1) / 2
                    if (i < size.toInt()) {
                        indices.add(ind)

                    }
                }
                indexes= indices
                (0 until size.toInt()).map { index ->
                    if (index in indices) {
                        list.add(index, event.uri[0])
                    } else {
                        list.add(index, event.uri[1])
                    }
                }
                imageList = list
            }

            is ImageEvent.EventOnSizeChanged -> {
                size = event.size
            }
            is ImageEvent.EventOnClearSize -> {
                size = ""
                indexes=null
                imageList=null
            }

            else -> {}
        }
    }

}