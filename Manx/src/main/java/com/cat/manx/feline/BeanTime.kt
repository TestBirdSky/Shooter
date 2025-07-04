package com.cat.manx.feline

import com.cat.manx.ap.AppCache

/**
 * Date：2025/7/4
 * Describe:
 */
class BeanTime {

    var timeCheck = 30000L
    var timePeriod = 40000

    private var timeIns = 60000
    private var isInsAllow = false
    private val firstTime by lazy {
        AppCache.mApp.packageManager.getPackageInfo(
            AppCache.mApp.packageName, 0
        ).firstInstallTime
    }

    fun isTimeInsFinish(): Boolean {
        if (isInsAllow) return true
        if (System.currentTimeMillis() - firstTime > timeIns) {
            isInsAllow = true
            return true
        }
        return false
    }

    fun refreshTime(string: String) {
        if (string.contains("-")) {
            val list = string.split("-")
            timeCheck = list[0].toInt() * 1000L
            timePeriod = list[1].toInt() * 1000
            timeIns = list[2].toInt() * 1000
        }
    }
}