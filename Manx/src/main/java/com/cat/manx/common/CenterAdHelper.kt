package com.cat.manx.common

import android.content.Context
import com.cat.manx.CacheHelper
import com.cat.manx.common.sdk.PangleImpl
import com.cat.manx.common.sdk.TradPlusAdImpl

/**
 * Date：2025/7/3
 * Describe:
 */
class CenterAdHelper(val context: Context) {
    private val mPangleImpl by lazy { PangleImpl(context) }
    private val mTradPlusAdImpl by lazy { TradPlusAdImpl(context) }

    fun loadAd() {
        if (CacheHelper.adIdStr.isBlank()) return

    }


}