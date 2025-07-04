package com.cat.manx.common

import android.content.Context
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails
import com.cat.manx.CacheHelper
import com.cat.manx.cache.StringCacheImpl
import com.cat.manx.net.CollarNetwork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Date：2025/7/2
 * Describe:
 */
class RefererFetch {
    private val postJsonEvent by lazy { PostJsonEvent() }

    fun fetch(context: Context) {
        if (CacheHelper.mRefStr.isBlank()) {
            CoroutineScope(Dispatchers.IO).launch {
                while (CacheHelper.mRefStr.isBlank()) {
                    fetchReferrer(context)
                    delay(20000)
                }
            }
        } else {
            postJsonEvent.postRef()
        }
    }

    private fun fetchReferrer(context: Context) {
        val referrerClient = InstallReferrerClient.newBuilder(context).build()
        referrerClient.startConnection(object : InstallReferrerStateListener {
            override fun onInstallReferrerSetupFinished(p0: Int) {
                runCatching {
                    if (p0 == InstallReferrerClient.InstallReferrerResponse.OK) {
                        val response: ReferrerDetails = referrerClient.installReferrer
                        CacheHelper.mRefStr = response.installReferrer
                        // todo del
                        if (Tools.IS_TEST) {
                            Tools.log("mGoogleReferStr-->${CacheHelper.mRefStr}")
                        }
                        postJsonEvent.postRef()
                        referrerClient.endConnection()
                    } else {
                        referrerClient.endConnection()
                    }
                }.onFailure {
                    referrerClient.endConnection()
                }
            }

            override fun onInstallReferrerServiceDisconnected() = Unit
        })
    }

}