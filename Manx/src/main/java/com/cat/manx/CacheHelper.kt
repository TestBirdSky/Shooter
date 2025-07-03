package com.cat.manx

import android.content.SharedPreferences
import com.cat.manx.ap.AppCache
import com.cat.manx.cache.StringCacheImpl

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

}
