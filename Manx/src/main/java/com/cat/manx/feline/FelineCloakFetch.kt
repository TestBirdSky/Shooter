package com.cat.manx.feline

import com.cat.manx.CacheHelper
import com.cat.manx.ap.AppCache
import com.cat.manx.cache.StringCacheImpl
import com.cat.manx.common.Tools
import com.cat.manx.net.NetworkJsonHelper
import com.cat.manx.net.StringToOther
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * Date：2025/7/3
 * Describe:
 */
class FelineCloakFetch {
    private val felineUrl = "https://sino.biltzshotter.com/iffy/asinine/paula"
    private val okHttpClient by lazy { OkHttpClient() }
    private val mJsonHelper by NetworkJsonHelper("cloak_str")
    private var mStringToOther by StringToOther("cloak", felineUrl)
    private val mIoScope by lazy { CoroutineScope(Dispatchers.IO + SupervisorJob()) }

    companion object {
        private var mCloakInfoStr by StringCacheImpl()

        fun isCloakOver(): Int {
            if (mCloakInfoStr.isBlank()) return -1
            if (mCloakInfoStr == "faint") return 50
            return 100
        }
    }

    fun fetchFeline() {
        if (mCloakInfoStr.isNotBlank()) return
        mStringToOther = mJsonHelper
        val req = mStringToOther as Request
        reqOk(req, 4) {
            mIoScope.launch {
                delay(9000)
                fetchFeline()
            }
        }
    }

    private fun reqOk(request: Request, num: Int = 2, failed: () -> Unit) {
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Tools.log("reqOk--->$e")
                if (num > 0) {
                    mIoScope.launch {
                        delay(6000)
                        reqOk(request, num - 1, failed)
                    }
                } else {
                    failed.invoke()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string() ?: ""
                Tools.log("response--->$body")
                if (response.code == 200) {
                    mCloakInfoStr = body
                } else {
                    if (num > 0) {
                        mIoScope.launch {
                            delay(20000)
                            reqOk(request, num - 1, failed)
                        }
                    } else {
                        failed.invoke()
                    }
                }
            }
        })
    }

}