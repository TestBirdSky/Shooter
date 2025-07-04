package com.cat.manx.net

import com.cat.manx.cache.BoolCacheImpl
import com.cat.manx.common.CenterAdHelper
import com.cat.manx.common.Tools
import com.cat.manx.feline.FelineActivityCache
import com.cat.manx.feline.FelineCloakFetch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Date：2025/7/4
 * Describe:
 */
class CenterHelper {
    private val mIoScope = CoroutineScope(Dispatchers.IO)
    private val mNetwork = CollarNetwork()
    private var isPostChesCenter by BoolCacheImpl()
    private var isOver = false

    private fun postDebug() {
        if (isPostChesCenter) return
        if (Tools.isOpenedDebug()) {
            mNetwork.eventPost("ches_sys")
            isPostChesCenter = true
        }
    }

    fun startAction() {
        mIoScope.launch {
            delay(300)
            postDebug()
            while (isOver.not()) {
                if (FelineCloakFetch.isCloakOver() == 50) {
                    Tools.log("claok ---> over")
                    break
                }
                if (FelineCloakFetch.isCloakOver() == 100) {
                    Tools.log("claok ---> go")
                    checkNext()
                }
                delay(2000)
            }
        }
    }

    private suspend fun checkNext() {
        while (isOver.not()) {
            if (Tools.isUAllow()) {
                Tools.log("claok ---> next")
                FelineActivityCache.isCanFinish = true
                Tools.mCatUserInfo.createFile()
                isOver = true
            }
            delay(3000)
        }

    }

}