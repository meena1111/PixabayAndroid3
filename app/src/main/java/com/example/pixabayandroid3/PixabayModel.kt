package com.example.pixabayandroid3

data class PixabayModel(
    val hits: List<ImageModel>
)

data class ImageModel(
    val largeImageURL: String
)