package com.cat.manx

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.cat.manx.common.Tools
import com.cat.manx.feline.FelineActivityCache
import com.cat.manx.feline.QueenHelper
import com.tradplus.ads.open.TradPlusSdk

/**
 * Date：2025/7/2
 * Describe:
 */
class QueenActivityLifeCall(context: Context, idTradPlus: String) :
    Application.ActivityLifecycleCallbacks {
    private val mQueenHelper by lazy { QueenHelper() }
    private var numPage = 0

    init {
        TradPlusSdk.initSdk(context, idTradPlus)
        mQueenHelper.setNotification(context)
    }


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Tools.log("onActivityCreated--->$activity")
        mQueenHelper.pageAdd(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        mQueenHelper + activity
        numPage++
        Tools.log("onActivityStarted--->$activity")
    }

    override fun onActivityResumed(activity: Activity) {
        Tools.log("onActivityResumed--->$activity")
    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {
        Tools.log("onActivityStopped--->$activity")
        numPage--
        if (numPage <= 0) {
            numPage = 0
            mQueenHelper.finish()
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

    override fun onActivityDestroyed(activity: Activity) {
        Tools.log("onActivityDestroyed--->$activity")
        FelineActivityCache.listActivity.remove(activity)
    }
}