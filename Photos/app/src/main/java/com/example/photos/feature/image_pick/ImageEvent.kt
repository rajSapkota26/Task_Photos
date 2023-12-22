package com.example.photos.feature.image_pick

import android.net.Uri

sealed class ImageEvent {
    data class EventOnImagePick(val uri: List<Uri>): ImageEvent()
    data class EventOnSizeChanged(val size:String): ImageEvent()
    object  EventOnClearSize: ImageEvent()
}