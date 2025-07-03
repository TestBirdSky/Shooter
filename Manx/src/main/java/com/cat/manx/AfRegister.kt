package com.cat.manx

import android.content.Context
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.cat.manx.ap.AppCache

/**
 * Date：2025/7/2
 * Describe:
 */
class AfRegister(val afId: String, val isDebug: Boolean = false) : AppsFlyerConversionListener {
    private val mAndroidId by lazy { CacheHelper.mAndroidIdStr }
    private lateinit var mContext: Context

    init {
        AppsFlyerLib.getInstance().setDebugLog(isDebug)
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
        val status = p0["af_status"]?.toString() ?: ""
        if (status.contains("Non-organic", true)) {

        }
    }

    override fun onConversionDataFail(p0: String?) {

    }

    override fun onAppOpenAttribution(p0: MutableMap<String, String>?) = Unit
    override fun onAttributionFailure(p0: String?) = Unit


    fun openService() {
        AppCache.startNot(mContext)
    }

}