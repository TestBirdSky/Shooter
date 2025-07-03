package com.cat.manx.net

import com.cat.manx.CacheHelper
import org.json.JSONObject
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Date：2025/7/2
 * Describe:
 */
class NetworkJsonHelper(val type: String) : ReadOnlyProperty<Any, String> {

    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return when (type) {
            "commonjs" -> getCommonJS().toString()
            "Ref_js" -> {
                getCommonJS().apply {
                    put("", CacheHelper.mRefStr)
                }.toString()
            }
            else -> ""
        }
    }

    private fun getCommonJS(): JSONObject {
        return JSONObject().apply {

        }
    }

}