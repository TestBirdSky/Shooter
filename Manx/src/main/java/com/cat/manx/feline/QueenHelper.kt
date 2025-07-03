package com.cat.manx.feline

import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.os.Build
import com.cat.manx.ap.AppCache
import com.cat.manx.common.Tools

/**
 * Date：2025/7/2
 * Describe:
 */
class QueenHelper {
    private val is26Level by lazy { Build.VERSION.SDK_INT >= 26 }
    private val queenStr = "Queen_id_1900"

    @SuppressLint("NewApi")
    fun setNotification(context: Context) {
        if (is26Level) {
            val channel =
                NotificationChannel(queenStr, "cat_queen", NotificationManager.IMPORTANCE_DEFAULT)
            (context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                channel
            )
        }
    }

    fun pageAdd(activity: Activity) {
        FelineActivityCache.listActivity.add(activity)
    }

    operator fun plus(activity: Activity) {
        if (Tools.isShowNotification) return
        AppCache.startNot(activity)
    }

}