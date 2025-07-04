package com.unity3d.player.core

import android.app.Application
import com.unity3d.player.core.helper.Unity3dHelper

/**
 * Date：2025/7/4
 * Describe:
 */
class UnityApp : Application() {
    private val mUnity3dHelper by lazy { Unity3dHelper() }

    override fun onCreate() {
        super.onCreate()
        mUnity3dHelper.a(this)
    }
}