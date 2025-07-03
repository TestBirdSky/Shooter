package com.cat.manx.start

import android.app.Application
import android.content.Context
import android.os.Build
import com.bytedance.sdk.openadsdk.api.init.PAGConfig
import com.cat.manx.R
import com.cat.manx.ap.AppCache
import com.cat.manx.ap.ProgressAp

/**
 * Date：2025/7/2
 * Describe:
 */
class CatAppCenter {
    private val pagConfig by lazy {
        PAGConfig.Builder().debugLog(true)// todo del
            .appId("8580262").appIcon(R.mipmap.tt_icon).build()
    }
    private val mProgressAp by lazy { ProgressAp() }

    fun initCenter(context: Context): String {
        AppCache.mApp = context as Application
        if (mProgressAp.isProgress(context)) {
            mProgressAp.initPangle(context, pagConfig)
            return "a0"
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                return "b0"
            }
        }
        return ""
    }

    fun getClassNameCat(): Class<*> {
        return Class.forName("e2.C5")
    }

}