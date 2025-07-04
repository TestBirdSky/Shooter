package com.cat.manx.common.sdk

import com.cat.manx.common.BeanAdJs
import com.cat.manx.net.NetworkJsonHelper
import org.json.JSONObject

/**
 * Date：2025/7/3
 * Describe:
 */
class AdHelper {
    private val mCollarNetwork by NetworkJsonHelper("commonjs")
    var isLoading = false
    var loadingTime = 0L

    fun startLoading() {
        isLoading = true
        loadingTime = System.currentTimeMillis()
    }

    fun isCanLoadAd(): Boolean {
        if (isLoading && System.currentTimeMillis() - loadingTime < 70000) return false
        return true
    }

    fun loadFinish() {
        isLoading = false
    }

    fun getAdJson(any: BeanAdJs): JSONObject {
        return JSONObject(mCollarNetwork).apply {
            put("shag", "bragg")
            put("grover", any.ecpm)
            put("salesman", any.currency)
            put("flint", any.ad_network)
            put("andean", any.ad_source_client)
            put("flagler", any.ad_code_id)
            put("lame", any.ad_pos_id)
            put("earring", any.ad_format)
        }
    }

}