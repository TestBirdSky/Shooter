package com.cat.manx.cache

import android.content.Intent

/**
 * Date：2025/7/4
 * Describe:
 */
class BHelper {

    fun getI(intent: Intent): Intent? {
        val eIntent = intent.getParcelableExtra("Claw") as Intent? //注意:广播接收key(改成你们自己提供的字段)
        return eIntent
    }
}