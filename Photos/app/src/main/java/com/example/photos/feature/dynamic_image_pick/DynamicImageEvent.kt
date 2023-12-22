package com.example.photos.feature.dynamic_image_pick

import android.net.Uri

sealed class DynamicImageEvent {
    data class EventOnImagePick(val uri: List<Uri>): DynamicImageEvent()
    data class EventOnSizeChanged(val size:String): DynamicImageEvent()
}