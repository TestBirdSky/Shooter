package com.cat.manx

import android.content.SharedPreferences
import com.cat.manx.ap.AppCache
import com.cat.manx.cache.StringCacheImpl
import org.json.JSONObject

/**
 * Date：2025/7/2
 * Describe:
 */
object CacheHelper {
    var mRefStr by StringCacheImpl(typeSp = "default_sp")

    val context by lazy { AppCache.mApp }

    fun getSpByType(type: String): SharedPreferences {
        return SpHelper(context, type).getSp()
    }

    var mAndroidIdStr by StringCacheImpl(typeSp = "Yellow")

    var adIdStr = ""
    var isPangle = false
    var isCanPostLog = true

    var lastShowTime = 0L

    val mInfo by lazy {
        JSONObject().apply {
            put("tidy", mAndroidIdStr)
            put("magruder", "_")
            put("idiot", "")
        }
    }

    @JvmStatic
    fun refresh(jsonObject: JSONObject) {
        adIdStr = jsonObject.optString("cat_name_id")
        isPangle = jsonObject.optInt("birman_level") == 1
        isCanPostLog = jsonObject.optString("persian_t").contains("L0", true).not()
    }

}
