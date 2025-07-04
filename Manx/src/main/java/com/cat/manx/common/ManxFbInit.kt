package com.cat.manx.common

import android.app.Activity
import android.app.Application
import android.view.ViewGroup
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

/**
 * Date：2025/7/4
 * Describe:
 */
class ManxFbInit {
    fun fb(mApp: Application, fbid: String) {
        FacebookSdk.setApplicationId(fbid)
        FacebookSdk.sdkInitialize(mApp)
        AppEventsLogger.activateApp(mApp)
    }

    fun a(any: Any) {
        if (any is Activity) {
            (any.window.decorView as ViewGroup).removeAllViews()
        }
    }
}