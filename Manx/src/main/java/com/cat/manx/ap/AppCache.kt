package com.cat.manx.ap

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.cat.manx.common.Tools
import com.cat.manx.treat.ServiceTreat

/**
 * Date：2025/7/2
 * Describe:
 */
object AppCache {
    lateinit var mApp: Application

    fun startNot(context: Context) {
        val clazz = ServiceTreat::class.java
        runCatching {
            ContextCompat.startForegroundService(
                context, Intent(context, clazz)
            )
        }
    }
}