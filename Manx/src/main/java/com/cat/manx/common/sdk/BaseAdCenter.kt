package com.cat.manx.common.sdk

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
        postEvent("reqadvertise${tag()}", null)
    }

    protected fun isCanLoadAd(): Boolean {
        return mAdHelper.isCanLoadAd()
    }

    protected fun loadFinish(msg: String) {
        mAdHelper.loadFinish()
        if (msg.isBlank()) {
            postEvent("getadvertise${tag()}", null)
        } else {
            postEvent("getfail${tag()}", msg)
        }
    }

    protected fun adShow(any: Any?) {

    }
}