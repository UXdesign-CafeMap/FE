package com.example.cafemap.api.model.dto

import com.google.gson.annotations.SerializedName
data class NicknameDto(
    @SerializedName("words")
    val words: List<String>,
    @SerializedName("seed")
    val seed: String
)