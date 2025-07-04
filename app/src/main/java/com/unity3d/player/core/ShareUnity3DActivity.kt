package com.unity3d.player.core

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.unity3d.player.UnityPlayerActivity

/**
 * Date：2025/7/4
 * Describe:
 */
class ShareUnity3DActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, UnityPlayerActivity::class.java))
        finish()
    }
}