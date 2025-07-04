package com.cat.manx.common.sdk

import android.app.Activity
import android.content.Context
import com.bytedance.sdk.openadsdk.api.init.PAGSdk
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAd
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAdInteractionCallback
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAdLoadCallback
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialRequest
import com.bytedance.sdk.openadsdk.api.model.PAGAdEcpmInfo
import com.bytedance.sdk.openadsdk.api.model.PAGErrorModel
import com.cat.manx.common.Tools
import com.cat.manx.feline.FelineActivityCache
import com.cat.manx.net.CollarNetwork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject


/**
 * Date：2025/7/3
 * Describe:
 */
class PangleImpl(val context: Context) : BaseAdCenter() {
    private var mPAGInterstitialAd: PAGInterstitialAd? = null
    private val mCollarNetwork by lazy { CollarNetwork() }

    override fun tag(): String {
        return "1"
    }

    override fun postAdJson(jsonObject: JSONObject) {
        mCollarNetwork.postJson(jsonObject, 3)
    }

    override fun postEvent(name: String, value: String?) {
        mCollarNetwork.eventPost(name, value)
    }

    fun load(id: String) {
        if (PAGSdk.isInitSuccess().not()) return
        if (isCanLoadAd().not()) return
        if (isReadyAd()) {
            Tools.log("pangle ad is ready")
            return
        }
        startLoad()
        PAGInterstitialAd.loadAd(id,
            PAGInterstitialRequest(context),
            object : PAGInterstitialAdLoadCallback {
                override fun onError(pagErrorModel: PAGErrorModel) {
                    loadFinish("${pagErrorModel.errorCode}_${pagErrorModel.errorMessage}")
                }

                override fun onAdLoaded(pagInterstitialAd: PAGInterstitialAd) {
                    loadFinish("")
                    mPAGInterstitialAd = pagInterstitialAd
                }
            })
    }

    fun isReadyAd(): Boolean {
        return mPAGInterstitialAd?.isReady == true
    }

    private var showJob: Job? = null

    fun showAd(activity: Activity, close: () -> Unit) {
        val ad = mPAGInterstitialAd
        if (ad != null && isReadyAd()) {
            val time = System.currentTimeMillis()
            showJob = CoroutineScope(Dispatchers.IO).launch {
                delay(6000)
                postEvent("show", "6")
            }
            ad.setAdInteractionCallback(object : PAGInterstitialAdInteractionCallback() {
                override fun onAdReturnRevenue(pagAdEcpmInfo: PAGAdEcpmInfo?) {
                    super.onAdReturnRevenue(pagAdEcpmInfo)
                    FelineActivityCache.isShowAd = true
                    showJob?.cancel()
                    postEvent("show", "${(System.currentTimeMillis() - time) / 1000}")
                    adShow(pagAdEcpmInfo)
                }

                override fun onAdShowFailed(pagErrorModel: PAGErrorModel) {
                    super.onAdShowFailed(pagErrorModel)
                    postEvent(
                        "showfailer",
                        "${pagErrorModel.errorCode}_${pagErrorModel.errorMessage}"
                    )
                    close.invoke()
                }

                override fun onAdDismissed() {
                    super.onAdDismissed()
                    close.invoke()
                }
            })
            ad.show(activity)
            mPAGInterstitialAd = null
        } else {
            activity.finishAndRemoveTask()
        }
    }
}