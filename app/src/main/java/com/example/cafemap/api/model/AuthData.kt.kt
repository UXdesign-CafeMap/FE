package com.example.cafemap.api.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class SignUpRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)

data class SignInRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
