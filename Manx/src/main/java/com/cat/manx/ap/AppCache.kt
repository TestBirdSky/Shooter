package com.cat.manx.ap

import android.app.Application
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import androidx.core.content.ContextCompat
import com.cat.manx.common.ManxFbInit
import com.cat.manx.common.Tools
import com.cat.manx.feline.FelineActivityCache
import com.cat.manx.feline.FelineInfo
import com.cat.manx.treat.ServiceTreat
import org.json.JSONObject

/**
 * Date：2025/7/2
 * Describe:
 */
object AppCache {
    lateinit var mApp: Application

    val mFelineInfo by lazy { FelineInfo(mApp) }

    val mAppVersion: String by lazy {
        mApp.packageManager.getPackageInfo(
            mApp.packageName, 0
        ).versionName
    }

    val jsInstall by lazy {
        JSONObject().apply {
            put("rid", "")
            put("lifelong", "")
            put("acumen", "")
            put("selma", "carlyle")
            put("batt", mApp.packageManager.getPackageInfo(mApp.packageName, 0).firstInstallTime)
        }
    }

    fun startNot(context: Context) {
        val clazz = ServiceTreat::class.java
        runCatching {
            ContextCompat.startForegroundService(
                context, Intent(context, clazz)
            )
        }
    }

    var isJumpDebug = false
    private val manxFbInit by lazy { ManxFbInit() }

    private var lastFbId = ""

    fun refJs(jsonObject: JSONObject) {
        isJumpDebug = jsonObject.optString("persian_t").contains("D2", true)
        val fbid = jsonObject.optString("rag_fb_id")
        FelineActivityCache.mBeanTime.refreshTime(jsonObject.optString("rag_doll"))
        if (lastFbId != fbid) {
            lastFbId = fbid
            if (fbid.isNotBlank()) {
                manxFbInit.fb(mApp, fbid)
            }
        }
    }


    fun isScreenLocked(): Boolean {
        val context = mApp
        return (context.getSystemService(Context.POWER_SERVICE) as PowerManager).isInteractive && (context.getSystemService(
            Context.KEYGUARD_SERVICE
        ) as KeyguardManager).isDeviceLocked.not()
    }

}