package com.shenjing.colordoku.util

import android.content.Context
import android.content.SharedPreferences

import com.shenjing.colordoku.App

object SpHelper {
    val instance: SharedPreferences by lazy {
        App.instance.getSharedPreferences("data", Context.MODE_PRIVATE)
    }

    private fun getLeavedTime(): Long {
        return instance.getLong("leavedTime", 0L)
    }

    fun hasGameData(): Boolean {
        return getLeavedTime() != 0L
    }
}