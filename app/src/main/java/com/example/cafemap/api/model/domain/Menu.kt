package com.example.cafemap.api.model.domain

import com.google.gson.annotations.SerializedName

data class Menu (
    @SerializedName("menuName") val menuName: String,
    @SerializedName("menuPrice") val menuPrice: String,
)
