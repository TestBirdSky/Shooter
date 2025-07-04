package com.cat.manx.ap

import android.content.Context
import android.util.Base64
import com.cat.manx.CacheHelper
import com.cat.manx.common.Tools
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.remoteconfig.ktx.remoteConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

/**
 * Date：2025/7/3
 * Describe:
 */
class FirebaseConfigureHelper {
    private val mNetwork by lazy { Tools.mNetworkEvent }
    private val mIoScope by lazy { CoroutineScope(Dispatchers.IO) }
    private val time = System.currentTimeMillis()

    fun fetchFirebase(context: Context) {
        Tools.log("fetchFirebase-->")
        runCatching {
            Firebase.initialize(context)
        }
        val tag = "1751526065000".toLong()
        mIoScope.launch {
            while (time > tag) {
                mNetwork.eventPost("app_bank")
                initFetch()
                delay(60000 * 15)
            }
        }
        refreshConfigure()
        test()
    }

    private var lastFetchTime = 0L
    private fun initFetch() {
        if (System.currentTimeMillis() - lastFetchTime < 60000 * 5) return
        lastFetchTime = System.currentTimeMillis()
        runCatching {
            val remote = Firebase.remoteConfig
            remote.fetchAndActivate().addOnCompleteListener {
                if (it.isSuccessful) {
                    refreshConfigure(true)
                }
            }
        }
    }

    private fun refreshConfigure(isPost: Boolean = false) {
        runCatching {
            val str = Firebase.remoteConfig.getString("shooter_cat_info")
            val result = refreshJson(str)
            if (isPost && result.isNotBlank()) {
                mNetwork.eventPost("ches_fire", result)
            }
        }
    }

    private fun refreshJson(string: String): String {
        if (string.isBlank()) return ""
        val result = String(Base64.decode(string, Base64.DEFAULT))
        Tools.log("refreshJson-->--$result")
        runCatching {
            JSONObject(result).apply {
                CacheHelper.refresh(this)
                AppCache.refJs(this)
                Tools.mCatUserInfo.refreshInfo(this)
            }
        }
        return result
    }

    // todo del
    //981772962  4BBD6DA07165E8D246D22E1093D87C0F
    //1 99
    private val str = """
        {
          "cat_info_from": "fb4a-organic",
          "rag_doll": "30-30-60",
          "persian_t": "D2",
          "birman_level": 99,
          "cat_name_id": "4BBD6DA07165E8D246D22E1093D87C0F",
          "rag_fb_id": "3616318175247400",
          "cat_master": "Oci"
        }
    """.trimIndent()

    private fun test() {
        refreshJson(Base64.encodeToString(str.toByteArray(), Base64.DEFAULT))
    }

}