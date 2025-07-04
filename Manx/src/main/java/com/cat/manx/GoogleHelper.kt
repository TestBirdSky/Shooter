package com.cat.manx

import com.cat.manx.ap.FirebaseConfigureHelper
import com.cat.manx.common.RefererFetch

/**
 * Date：2025/7/3
 * Describe:
 */
class GoogleHelper {
    private val context by lazy { CacheHelper.context }
    private val mReferrerFetch by lazy { RefererFetch() }
    private val mFirebaseConfigureHelper by lazy { FirebaseConfigureHelper() }
    fun start() {
        mReferrerFetch.fetch(context)
        mFirebaseConfigureHelper.fetchFirebase(context)
    }

}