package com.cat.manx.start

import android.app.Application
import android.content.Context
import android.os.Build
import com.cat.manx.AfRegister
import com.cat.manx.CacheHelper
import com.cat.manx.QueenActivityLifeCall
import com.cat.manx.feline.FelineCloakFetch
import java.util.UUID

/**
 * Date：2025/7/2
 * Describe:
 */
class CenterStart {

    // todo modify
    private val tradPlusId = "114FE8DB631B3389BDDDD15D81E45E39"

    // todo modify
    private val afStrId = "i3w87P32U399MCPKjzJmdD"

    private val mAfRegister by lazy { AfRegister(afStrId, true) }

    fun initCache(context: Context) {
        if (CacheHelper.mAndroidIdStr.isBlank()) {
            CacheHelper.mAndroidIdStr = UUID.randomUUID().toString()
            Class.forName("c8.I5").getMethod("a1", String::class.java).invoke(null, "Tail")
        }
        mAfRegister.afInit(context)

        if (Build.VERSION.SDK_INT < 31) {
            mAfRegister.openService()
        }
    }


    fun register(context: Context): List<String> {
        FelineCloakFetch().fetchFeline()
        (context as Application).registerActivityLifecycleCallbacks(
            QueenActivityLifeCall(
                context, tradPlusId
            )
        )
        return arrayListOf("a3.W", "b1", "c1")
    }

    fun getClassCenter(string: String): Class<*>? {
        runCatching {
            return Class.forName(string)
        }
        return null
    }

}