package com.cat.manx

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * Date：2025/7/2
 * Describe:
 */
class SpHelper(private val context: Context, private val spType: String = "") {
    private val fileName = "Cat"
    private val fileNameDefault = "Manx"
    private val mDefaultSp by lazy { context.getSharedPreferences(fileNameDefault, 0) }

    private val masterKey by lazy {
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
    }

    fun getSp(): SharedPreferences {
        return when (spType) {
            "default_sp" -> mDefaultSp
            else -> createEncryptedPrefs()
        }
    }

    private fun createEncryptedPrefs(): SharedPreferences {
        return EncryptedSharedPreferences.create(
            context,
            fileName,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    }

}