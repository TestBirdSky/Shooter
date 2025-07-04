package com.cat.manx.net

import android.os.Build
import com.cat.manx.CacheHelper
import com.cat.manx.ap.AppCache
import com.cat.manx.feline.FelineActivityCache
import com.tradplus.ads.base.config.request.BiddingRequestInfo.App
import org.json.JSONObject
import java.util.UUID
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Date：2025/7/2
 * Describe:
 */
class NetworkJsonHelper(val type: String) : ReadOnlyProperty<Any, String> {
    private val tubuleJs by lazy {
        JSONObject().apply {
            put("sofia", Build.BRAND)
            put("map", Build.MODEL)
            put("heredity", Build.MANUFACTURER)
            put("francis", Build.VERSION.RELEASE)
        }
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return when (type) {
            "commonjs" -> getCommonJS().toString()
            "Ref_js" -> {
                getCommonJS().apply {
                    put("cordial", JSONObject(AppCache.jsInstall.toString()).apply {
                        put("hatfield", CacheHelper.mRefStr)
                        put("goa", 0L)
                        put("pair", 0L)
                        put("schottky", 0L)
                        put("obvious", 0L)
                        put("glide", 0L)
                        put("jacobean", false)
                    })
                }.toString()
            }

            "cloak_str" -> {
                getBody().toString()
            }

            "nameWeb"->{
                "x2.P1"
            }

            "feline" -> {
                Class.forName("c8.I5").getMethod("a1", String::class.java).invoke(null, "Ear")
                return JSONObject().apply {
                    put("name", "x2.P1")
                    put("fun_name", "i1")
                    put("value", Any::class.java.canonicalName)
                }.toString()
            }

            "popInfo" -> {
                val ready = FelineActivityCache.mCenterAdHelper.isReadyAd()
                var name = ""
                val delTime = if (FelineActivityCache.isShowAd || ready.isNotBlank()) {
                    name = "test_api"
                    FelineActivityCache.finishPage()
                } else {
                    0L
                }
                return JSONObject().apply {
                    put("ad_status", ready)
                    put("event_2", name)
                    put("delTime", delTime)
                }.toString()
            }

            else -> ""
        }
    }

    private fun getBody(): JSONObject {
        return JSONObject().apply {
            put("shafer", "com.biltzshotter.skyforce.bfsrun")
            put("flaky", "amaze")
            put("window", AppCache.mAppVersion)
            put("cunard", CacheHelper.mAndroidIdStr)
            put("aphasic", System.currentTimeMillis())
        }
    }

    private fun getCommonJS(): JSONObject {
        return JSONObject().apply {
            put("tubule", tubuleJs.put("aphasic", System.currentTimeMillis()))
            put("intimate", JSONObject(CacheHelper.mInfo.toString()).apply {
                put("indira", UUID.randomUUID().toString())
            })
            put("nurse", JSONObject().apply {
                put("cunard", CacheHelper.mAndroidIdStr)
                put("window", AppCache.mAppVersion)
                put("shafer", AppCache.mApp.packageName)
                put("flaky", "amaze")
            })
        }
    }

}