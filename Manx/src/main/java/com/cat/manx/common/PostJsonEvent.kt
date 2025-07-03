package com.cat.manx.common

import com.cat.manx.cache.BoolCacheImpl
import com.cat.manx.net.CollarNetwork
import com.cat.manx.net.NetworkJsonHelper
import org.json.JSONObject

/**
 * Date：2025/7/3
 * Describe:
 */
class PostJsonEvent {
    private var isPostRef1 by BoolCacheImpl()
    private val collarNetwork by lazy { CollarNetwork() }
    private val mRefJson by NetworkJsonHelper("Ref_js")

    fun postRef() {
        if (isPostRef1) return
        collarNetwork.postJson(JSONObject(mRefJson), 30, success = {
            isPostRef1 = true
        })
    }

    fun postAdValue() {

    }

}