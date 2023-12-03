package com.example.cafemap.api.model.dto

import com.google.gson.annotations.SerializedName
data class NicknameDto(
    @SerializedName("nickname")
    val words: List<String>,
    @SerializedName("seed")
    val seed: String,
)