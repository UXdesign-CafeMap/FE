package com.example.cafemap.api.model.dto

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("password")
    val password: String
)

data class SignUpResponse(
    @SerializedName("memberId")
    val memberId: Int
)

data class SignInRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)

data class SignInResponse(
    @SerializedName("memberId")
    val memberId: Int
)