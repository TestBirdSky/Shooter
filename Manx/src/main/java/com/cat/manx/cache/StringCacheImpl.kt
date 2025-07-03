package com.cat.manx.cache

import android.content.SharedPreferences
import com.cat.manx.CacheHelper
import java.security.MessageDigest
import kotlin.reflect.KProperty

/**
 * Date：2025/7/2
 * Describe:
 */
class StringCacheImpl(val def: String = "", val typeSp: String = "") {
    private val mSp: SharedPreferences by lazy { CacheHelper.getSpByType(typeSp) }
    private var key = ""

    private fun getKey(name: String): String {
        if (key.isNotBlank()) return key
        val s = sha256("Cat_$name")
        key = s.take(9) + name.takeLast(3) + s.takeLast(5)
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

    operator fun getValue(me: Any?, p: KProperty<*>): String {
        return mSp.getString(getKey(p.name), def) ?: def
    }

    operator fun setValue(me: Any?, p: KProperty<*>, value: String) {
        mSp.edit().putString(getKey(p.name), value).apply()
    }

}