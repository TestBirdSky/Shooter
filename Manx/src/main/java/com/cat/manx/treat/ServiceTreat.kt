package com.cat.manx.treat

import a3.a0
import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import com.cat.manx.R

/**
 * Date：2025/7/3
 * Describe:
 */
class ServiceTreat : Service() {
    private var mNotification: Notification? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mNotification =
            a0.b(this).setCustomContentView(RemoteViews(packageName, R.layout.layout_spg_one))
                .build()
        if (Build.VERSION.SDK_INT < 34) {
            runCatching {
                startForeground(3091, mNotification)
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        runCatching {
            startForeground(3091, mNotification)
        }
        return START_STICKY
    }
}