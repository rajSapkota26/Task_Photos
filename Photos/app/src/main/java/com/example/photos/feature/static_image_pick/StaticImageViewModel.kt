package com.example.photos.feature.static_image_pick

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class StaticImageViewModel : ViewModel() {
    var imageList by mutableStateOf<List<Uri>?>(null)
    var triangleSequenceIndices by mutableStateOf(mutableSetOf<Int>())


    fun onEvent(event: StaticImageEvent) {
        when (event) {
            is StaticImageEvent.EventOnImagePick -> {
                val list = mutableListOf<Uri>()
                val indices = mutableSetOf<Int>()
                for (i in 1..55) {
                    val ind = i * (i + 1) / 2
                    if (ind <= 50) {
                        indices.add(ind)

                    }
                }
                triangleSequenceIndices = listOf(1,3,6,10,15,21).toSortedSet()

                (0 until 55).map { index ->
                    if (index in indices) {
                        list.add(index, event.uri[0])
                    } else {
                        list.add(index, event.uri[1])
                    }
                }
                imageList = list
            }

            else -> {}
        }
    }

}