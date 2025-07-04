package com.cat.manx.common.sdk

import com.bytedance.sdk.openadsdk.api.model.PAGAdEcpmInfo
import com.cat.manx.CacheHelper
import com.cat.manx.ap.AppCache
import com.cat.manx.common.BeanAdJs
import com.tradplus.ads.base.bean.TPAdInfo
import org.json.JSONObject

/**
 * Date：2025/7/3
 * Describe:
 */
abstract class BaseAdCenter {
    private val mAdHelper by lazy { AdHelper() }

    abstract fun tag(): String

    abstract fun postAdJson(jsonObject: JSONObject)

    abstract fun postEvent(name: String, value: String?)

    protected fun startLoad() {
        mAdHelper.startLoading()
        postEvent("ad_req${tag()}", null)
    }

    protected fun isCanLoadAd(): Boolean {
        return mAdHelper.isCanLoadAd()
    }

    protected fun loadFinish(msg: String) {
        mAdHelper.loadFinish()
        if (msg.isBlank()) {
            postEvent("ad_get${tag()}", null)
        } else {
            postEvent("ad_get_fail${tag()}", msg)
        }
    }

    protected fun adShow(any: Any?): Double {
        CacheHelper.lastShowTime = System.currentTimeMillis()
        any?.let {
            when (it) {
                is PAGAdEcpmInfo -> {
                    postAdJson(mAdHelper.getAdJson(getBean(it)))
                    return it.cpm.toDouble() / 1000
                }

                is TPAdInfo -> {
                    postAdJson(mAdHelper.getAdJson(getBeanTradPlus(it)))
                    return it.ecpm.toDouble() / 1000
                }

                else -> {
                    throw Exception("Not impl this ad show event")
                }
            }
        }
        return 0.0
    }

    private fun getBean(any: PAGAdEcpmInfo): BeanAdJs {
        return BeanAdJs(
            any.cpm.toDouble() * 1000,
            "USD",
            any.adnName,
            "pangle",
            any.placement,
            "pangle_interstitial",
            any.adFormat
        )
    }


    private fun getBeanTradPlus(any: TPAdInfo): BeanAdJs {
        return BeanAdJs(
            any.ecpm.toDouble() * 1000,
            "USD",
            any.adSourceName,
            "tradplus",
            any.adSourcePlacementId,
            "tradplus_interstitial",
            any.format
        )
    }
}