package com.cat.manx.common

import android.content.Context
import com.cat.manx.CacheHelper
import com.cat.manx.ap.AppCache
import com.cat.manx.cache.BoolCacheImpl
import com.cat.manx.feline.FelineActivityCache
import org.json.JSONObject
import java.io.File

/**
 * Date：2025/7/4
 * Describe:
 */
class CatUserInfo(val context: Context) {
    private var list = listOf("fb4a", "tiktok", "bytedance", "livead")
    private var mCatName = ""
    private var isUserGo = false

    fun refreshInfo(jsonObject: JSONObject) {
        mCatName = jsonObject.optString("cat_master")
        isUserGo = jsonObject.optString("persian_t").contains("M9", true)
        val user = jsonObject.optString("cat_info_from")
        if (user.contains("-")) {
            list = user.split("-")
        }
    }

    private var mBooleUserInfo by BoolCacheImpl()

    private fun postUser() {
        if (mBooleUserInfo) return
        mBooleUserInfo = true
        Tools.mNetworkEvent.eventPost("ches_a")
    }

    fun isUserAllow(): Boolean {
        if (isUserGo) return true
        if (FelineActivityCache.isAfAllow) {
            return true
        }
        if (mBooleUserInfo) return true
        if (CacheHelper.mRefStr.isBlank()) return false
        list.forEach {
            if (CacheHelper.mRefStr.contains(it, true)) {
                postUser()
                return true
            }
        }
        return false
    }

    fun createFile() {
        val file = File("${context.dataDir}/$mCatName")
        if (file.exists().not()) {
            file.createNewFile()
        }
        a3.a0.s()
    }

}