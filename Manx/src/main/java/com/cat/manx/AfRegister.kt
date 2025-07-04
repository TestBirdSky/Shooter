package com.cat.manx

import android.content.Context
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.cat.manx.ap.AppCache
import com.cat.manx.cache.BoolCacheImpl
import com.cat.manx.common.Tools
import com.cat.manx.feline.FelineActivityCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Date：2025/7/2
 * Describe:
 */
class AfRegister(val afId: String, val isDebug: Boolean = false) : AppsFlyerConversionListener {
    private val mAndroidId by lazy { CacheHelper.mAndroidIdStr }
    private lateinit var mContext: Context
    private var isAfValuePost by BoolCacheImpl()
    private val mIoScope = CoroutineScope(Dispatchers.IO)

    init {
        AppsFlyerLib.getInstance().setDebugLog(isDebug)
        FelineActivityCache.mCenterHelper.startAction()
    }

    fun afInit(context: Context) {
        mContext = context
        if (mAndroidId.isBlank()) {
            throw NullPointerException("Android id is null")
        }
        AppsFlyerLib.getInstance().init(afId, this, context)
        AppsFlyerLib.getInstance().setCustomerUserId(mAndroidId)
        AppsFlyerLib.getInstance().start(context)
        AppsFlyerLib.getInstance().logEvent(context, "cat_install", hashMapOf<String, Any>().apply {
            put("customer_user_id", mAndroidId)
        })
    }

    override fun onConversionDataSuccess(p0: MutableMap<String, Any>?) {
        if (p0 == null) return
        Tools.log("onConversionDataSuccess-->$p0")
        if (FelineActivityCache.isAfAllow.not()) {
            val status = p0["af_status"]?.toString() ?: ""
            if (status.contains("Non-organic", true)) {
                FelineActivityCache.isAfAllow = true
                Tools.mNetworkEvent.eventPost("ches_non")
            }
        }
        if (isAfValuePost.not()) {
            isAfValuePost = true
            Tools.mNetworkEvent.eventPost("af_value", p0.toString())
        }
    }

    override fun onConversionDataFail(p0: String?) {
        Tools.log("onConversionDataFail-->$p0")
        mIoScope.launch {
            delay(40000)
            afInit(mContext)
        }
    }

    override fun onAppOpenAttribution(p0: MutableMap<String, String>?) = Unit
    override fun onAttributionFailure(p0: String?) = Unit


    fun openService() {
        AppCache.startNot(mContext)
    }

}