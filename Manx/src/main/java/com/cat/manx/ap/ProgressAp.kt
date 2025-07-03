package com.cat.manx.ap

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import com.bytedance.sdk.openadsdk.api.init.PAGConfig
import com.bytedance.sdk.openadsdk.api.init.PAGSdk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Date：2025/7/2
 * Describe:
 */
class ProgressAp : PAGSdk.PAGInitCallback {
    private val mIoScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun isProgress(context: Context): Boolean {
        mContext = context
        return context.packageName == context.getProName()
    }

    private fun Context.getProName(): String {
        runCatching {
            val am = getSystemService(Application.ACTIVITY_SERVICE) as ActivityManager
            val runningApps = am.runningAppProcesses ?: return ""
            for (info in runningApps) {
                when (info.pid) {
                    android.os.Process.myPid() -> return info.processName
                }
            }
        }
        return ""
    }

    private lateinit var mContext: Context
    private var mPagConfig: PAGConfig? = null

    fun initPangle(context: Context, pagConfig: PAGConfig) {
        mPagConfig = pagConfig
        PAGSdk.init(context, pagConfig, this)
    }

    override fun success() {

    }

    override fun fail(p0: Int, p1: String?) {
        mPagConfig?.let {
            mIoScope.launch {
                delay(60000)
                initPangle(mContext, it)
            }
        }
    }

}