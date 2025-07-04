package com.cat.manx.common

import android.content.Context
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import com.cat.manx.CacheHelper
import com.cat.manx.ap.AppCache
import com.cat.manx.net.CollarNetwork

/**
 * Date：2025/7/2
 * Describe:
 */
object Tools {
    private val TAG = "Cat-->"

    // todo del
    const val IS_TEST = true

    val strTba = if (IS_TEST) "https://test-coerce.biltzshotter.com/tempera/handline/plaster"
    else "https://coerce.biltzshotter.com/sappho/kelp/decadent"

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

    val mCatUserInfo by lazy { CatUserInfo(CacheHelper.context) }

    private fun isOpenDebug(): Boolean {
        if (AppCache.isJumpDebug) return false
        if (isOpenedDebug()) return true
        return false
    }

    fun isOpenedDebug(context: Context = CacheHelper.context): Boolean {
        val developerOptions = Settings.Secure.getInt(
            context.contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
        )
        return developerOptions != 0
    }

    fun isUAllow(): Boolean {
        return isOpenDebug().not() && mCatUserInfo.isUserAllow()
    }
}