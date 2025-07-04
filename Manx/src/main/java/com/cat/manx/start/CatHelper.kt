package com.cat.manx.start

import com.cat.manx.ap.AppCache
import com.cat.manx.common.Tools
import com.cat.manx.feline.FelineActivityCache

/**
 * Date：2025/7/4
 * Describe:
 */
class CatHelper {
    val mNetworkEvent by lazy { Tools.mNetworkEvent }

    fun checkInfo(): Boolean {
        mNetworkEvent.eventPost("test_clock")
        if (AppCache.isScreenLocked().not()) return false
        mNetworkEvent.eventPost("test_lock")
        if (FelineActivityCache.mBeanTime.isTimeInsFinish().not()) {
            mNetworkEvent.eventPost("test_config", "ins_19")
            return false
        }
        FelineActivityCache.mCenterAdHelper.loadAd()
        return true
    }

}