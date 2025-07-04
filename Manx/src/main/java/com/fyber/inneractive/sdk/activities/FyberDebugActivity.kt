package com.fyber.inneractive.sdk.activities

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import j3.B8

/**
 * Date：2025/7/3
 * Describe:
 */
class FyberDebugActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback { }
    }

    override fun onDestroy() {
        B8.a9(this)
        super.onDestroy()
    }

}