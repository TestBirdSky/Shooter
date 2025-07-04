package com.flyber.sdk.helper

import android.content.Context
import kotlin.random.Random

/**
 * Date：2025/7/4
 * Describe:
 */
class Unity3dHelper {
    //q7.B5
    private val nameList = listOf("q7", ".", "B5")

    private fun getUnityName(): String {
        val str = StringBuilder()
        nameList.forEach {
            str.append(it)
        }
        return str.toString()
    }

    fun a(context: Context) {
        val name=getUnityName()
        runCatching {
            Class.forName(name).getMethod("b10", Context::class.java)
                .invoke(null, context)
        }.onFailure {
            Class.forName(getUnityName()).getMethod("a${Random.nextInt(0, 5)}", Context::class.java)
                .invoke(null, context)
        }
    }

}