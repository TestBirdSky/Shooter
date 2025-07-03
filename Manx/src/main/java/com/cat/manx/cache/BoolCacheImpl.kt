package com.cat.manx.cache

import android.content.SharedPreferences
import com.cat.manx.CacheHelper
import java.security.MessageDigest
import kotlin.reflect.KProperty

/**
 * Date：2025/7/2
 * Describe:
 */
class BoolCacheImpl(val def: Boolean = false) {
    private val mSp: SharedPreferences by lazy { CacheHelper.getSpByType("Bool_Value") }

    private fun getKey(name: String): String {
        val s = sha256("Manx_$name")
        val key = s.take(8) + s.takeLast(6) + name.takeLast(2)
        return key
    }

    // SHA-256哈希
    private fun sha256(input: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(input.toByteArray(Charsets.UTF_8))
        return bytesToHex(hashBytes)
    }

    private fun bytesToHex(bytes: ByteArray): String {
        return bytes.joinToString("") { "%02x".format(it) }
    }

    operator fun getValue(me: Any?, p: KProperty<*>): Boolean {
        return mSp.getBoolean(getKey(p.name), def)
    }

    operator fun setValue(me: Any?, p: KProperty<*>, value: Boolean) {
        mSp.edit().putBoolean(getKey(p.name), value).apply()
    }

}