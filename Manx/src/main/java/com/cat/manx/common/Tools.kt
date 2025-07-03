package com.cat.manx.common

import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.cat.manx.net.CollarNetwork

/**
 * Date：2025/7/2
 * Describe:
 */
object Tools {
    private val TAG = "Cat-->"

    // todo del
    const val IS_TEST = true

    public val strTba = if (IS_TEST) "" else ""

    fun log(msg: String) {
        if (IS_TEST) Log.e(TAG, msg)
    }

    val mNetworkEvent by lazy { CollarNetwork() }

    var isShowNotification = false

    fun getNot(context: Context): NotificationCompat.Builder {
        isShowNotification = true
        return mNetworkEvent.getNotification(context).setContentText(getTitle()).setOngoing(true)
            .setOnlyAlertOnce(true)
    }

    private val title = arrayOf("t", "i", "p", "s")

    private fun getTitle(): String {
        val str = StringBuilder()
        title.forEach {
            str.append(it)
        }
        return str.toString().replace("tips", "")
    }
}