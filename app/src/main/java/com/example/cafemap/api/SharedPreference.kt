package com.example.cafemap.api

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

fun getCafeId(context: Context): Int {
    val spf = context.getSharedPreferences("cafeId_spf", AppCompatActivity.MODE_PRIVATE)
    return spf.getInt("cafeId_spf", 0)
}

