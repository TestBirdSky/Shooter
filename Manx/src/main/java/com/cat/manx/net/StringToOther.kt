package com.cat.manx.net

import com.cat.manx.common.Tools
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Date：2025/7/2
 * Describe:
 */
class StringToOther(val type: String = "") : ReadWriteProperty<Any, Any?> {
    private var valueStr = ""

    override fun getValue(thisRef: Any, property: KProperty<*>): Any? {
        return when (type) {
            "request" -> strToRequest(valueStr)
            else -> null
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Any?) {
        valueStr = value.toString()
    }

    private fun strToRequest(body: String): Request {
        return Request.Builder().post(
            body.toRequestBody("application/json".toMediaType())
        ).url(Tools.strTba).build()
    }


}