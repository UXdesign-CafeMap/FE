package com.example.cafemap.api
class ApiResponse<T>(
    val code: Int,
    val status: Int,
    val message: String,
    val result: T
)