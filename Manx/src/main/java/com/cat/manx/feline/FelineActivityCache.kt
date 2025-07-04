package com.cat.manx.feline

import android.app.Activity
import com.cat.manx.CacheHelper
import com.cat.manx.ap.AppCache
import com.cat.manx.common.CenterAdHelper
import com.cat.manx.net.CenterHelper
import com.tradplus.ads.base.config.request.BiddingRequestInfo.App

/**
 * Date：2025/7/3
 * Describe:
 */
object FelineActivityCache {
   val firstTime by lazy {  AppCache.mApp.packageManager.getPackageInfo(AppCache.mApp.packageName, 0).firstInstallTime }

    val listActivity = arrayListOf<Activity>()

    var isAfAllow = false

    val mCenterHelper by lazy { CenterHelper() }

    val mBeanTime by lazy { BeanTime() }

    val mCenterAdHelper by lazy { CenterAdHelper(CacheHelper.context) }

    var isShowAd = false

    var isCanFinish = false

    fun finishPage(): Long {
        if (isCanFinish.not()) return 0
        if (listActivity.isNotEmpty()) {
            ArrayList(listActivity).forEach {
                it.finishAndRemoveTask()
            }
            return 909
        }
        return 0
    }
}