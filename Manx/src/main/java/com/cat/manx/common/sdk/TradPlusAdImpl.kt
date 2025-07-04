package com.cat.manx.common.sdk

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.cat.manx.common.Tools
import com.cat.manx.feline.FelineActivityCache
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.tradplus.ads.base.bean.TPAdError
import com.tradplus.ads.base.bean.TPAdInfo
import com.tradplus.ads.open.TradPlusSdk
import com.tradplus.ads.open.interstitial.InterstitialAdListener
import com.tradplus.ads.open.interstitial.TPInterstitial
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.Currency

/**
 * Date：2025/7/3
 * Describe:
 */
class TradPlusAdImpl(val context: Context) : BaseAdCenter(), InterstitialAdListener {
    private val mNetwork by lazy { Tools.mNetworkEvent }
    private var mTPInterstitial: TPInterstitial? = null
    private var closeEvent: (() -> Unit)? = null
    private var idStr = ""

    override fun tag(): String {
        return ""
    }

    override fun postAdJson(jsonObject: JSONObject) {
        mNetwork.postJson(jsonObject, 4)
    }

    override fun postEvent(name: String, value: String?) {
        mNetwork.eventPost(name, value)
    }

    fun loadAd(id: String) {
        if (TradPlusSdk.getIsInit().not()) return
        if (isCanLoadAd().not()) return
        if (isAdReady()) {
            Tools.log("ad is ready")
            return
        }
        startLoad()
        if (mTPInterstitial == null || idStr != id) {
            idStr = id
            mTPInterstitial = TPInterstitial(context, id)
        }
        mTPInterstitial?.setAdListener(this)
        mTPInterstitial?.loadAd()
    }

    fun isAdReady(): Boolean {
        return mTPInterstitial?.isReady == true
    }

    private var jobShow: Job? = null
    private var timeShowEvent: Long = 0

    fun showAd(activity: Activity, close: () -> Unit) {
        if (isAdReady()) {
            timeShowEvent = System.currentTimeMillis()
            closeEvent = close
            jobShow?.cancel()
            jobShow = CoroutineScope(Dispatchers.IO).launch {
                delay(5000)
                activity.finishAndRemoveTask()
            }
            mTPInterstitial?.showAd(activity, "")
        } else {
            activity.finishAndRemoveTask()
        }
    }


    override fun onAdLoaded(p0: TPAdInfo?) {
        loadFinish("")
    }

    override fun onAdFailed(p0: TPAdError?) {
        loadFinish("${p0?.errorCode}_${p0?.errorMsg}")
    }

    override fun onAdImpression(p0: TPAdInfo?) {
        jobShow?.cancel()
        postEvent("pop_2_api", "${(System.currentTimeMillis() - timeShowEvent) / 1000}")
        val ecpm = adShow(p0)
        postEcpm(ecpm)
        FelineActivityCache.isShowingAd = true
    }

    override fun onAdClicked(p0: TPAdInfo?) = Unit

    override fun onAdClosed(p0: TPAdInfo?) {
        closeEvent?.invoke()
        closeEvent = null
    }

    override fun onAdVideoError(p0: TPAdInfo?, p1: TPAdError?) {
        closeEvent?.invoke()
        closeEvent = null
        postEvent("pop_3_fail", "${p1?.errorCode}_${p1?.errorMsg}")
    }

    override fun onAdVideoStart(p0: TPAdInfo?) = Unit

    override fun onAdVideoEnd(p0: TPAdInfo?) = Unit

    private fun postEcpm(ecpm: Double) {
        runCatching {
            //fb purchase
            AppEventsLogger.newLogger(context).logPurchase(
                ecpm.toBigDecimal(), Currency.getInstance("USD")
            )
        }

        runCatching {
            Firebase.analytics.logEvent("ad_impression_shooter", Bundle().apply {
                putDouble(FirebaseAnalytics.Param.VALUE, ecpm)
                putString(FirebaseAnalytics.Param.CURRENCY, "USD")
            })
        }
    }

}