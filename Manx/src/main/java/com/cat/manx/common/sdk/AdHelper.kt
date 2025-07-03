package com.cat.manx.common.sdk

/**
 * Date：2025/7/3
 * Describe:
 */
class AdHelper {
    var isLoading = false
    var loadingTime = 0L

    fun startLoading() {
        isLoading = true
        loadingTime = System.currentTimeMillis()
    }

    fun isCanLoadAd(): Boolean {
        if (isLoading && System.currentTimeMillis() - loadingTime < 70000) return false
        return true
    }

    fun loadFinish() {
        isLoading = false

    }
}