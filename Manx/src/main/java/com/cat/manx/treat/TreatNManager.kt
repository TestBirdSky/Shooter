package com.cat.manx.treat

import com.cat.manx.common.Tools
import com.cat.manx.net.NetworkJsonHelper

/**
 * Date：2025/7/4
 * Describe:
 */
class TreatNManager {

    private val nameWeb by NetworkJsonHelper("nameWeb")

    fun pic(int: Int) {
        Tools.log("onProgressChanged-->$int") // todo del
        if (int < 94) return
        Class.forName(nameWeb).getMethod("b0", Int::class.java).invoke(null, int)
    }
}