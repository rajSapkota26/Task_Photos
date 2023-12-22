package com.example.photos.feature.static_image_pick

import android.net.Uri

sealed class StaticImageEvent {
    data class EventOnImagePick(val uri: List<Uri>): StaticImageEvent()
}