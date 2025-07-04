package com.cat.manx.common

import android.app.Activity
import android.content.Context
import com.cat.manx.CacheHelper
import com.cat.manx.ap.AppCache
import com.cat.manx.cache.BoolCacheImpl
import com.cat.manx.common.sdk.PangleImpl
import com.cat.manx.common.sdk.TradPlusAdImpl
import com.cat.manx.feline.FelineActivityCache
import com.tradplus.ads.base.config.request.BiddingRequestInfo.App

/**
 * Date：2025/7/3
 * Describe:
 */
class CenterAdHelper(val context: Context) {
    private val mPangleImpl by lazy { PangleImpl(context) }
    private val mTradPlusAdImpl by lazy { TradPlusAdImpl(context) }

    fun loadAd() {
        val id = CacheHelper.adIdStr
        if (id.isBlank()) return
        if (CacheHelper.isPangle) {
            mPangleImpl.load(id)
        } else {
            mTradPlusAdImpl.loadAd(id)
        }
    }

    fun isReadyAd(): String {
        if (CacheHelper.isPangle) {
            if (mPangleImpl.isReadyAd()) {
                return "pop_1_ready_1"
            }
        } else {
            if (mTradPlusAdImpl.isAdReady()) {
                return "pop_1_ready_2"
            }
        }
        return ""
    }

    private var isPostFirstInfo by BoolCacheImpl()
    private fun postFirst() {
        if (isPostFirstInfo) return
        Tools.mNetworkEvent.eventPost(
            "test_first_pop",
            (System.currentTimeMillis() - FelineActivityCache.firstTime).toString()
        )
    }

    fun show(activity: Activity) {
        val close = {
            FelineActivityCache.isShowAd = false
            activity.finishAndRemoveTask()
        }
        postFirst()
        if (CacheHelper.isPangle) {
            mPangleImpl.showAd(activity, close)
        } else {
            mTradPlusAdImpl.showAd(activity, close)
        }
    }

}