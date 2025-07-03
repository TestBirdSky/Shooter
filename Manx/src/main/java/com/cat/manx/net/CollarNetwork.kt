package com.cat.manx.net

import android.app.Notification
import android.content.Context
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.cat.manx.R
import com.cat.manx.common.Tools
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

/**
 * Date：2025/7/2
 * Describe:
 */
class CollarNetwork {
    private val mIoScope = CoroutineScope(Dispatchers.IO)
    private var mStringToOther by StringToOther("request")
    private val mCollarNetwork by NetworkJsonHelper("commonjs")
    private val mOkHttpClient by lazy { OkHttpClient() }

    fun eventPost(name: String, value: String? = null) {

    }

    fun postJson(jsonObject: JSONObject, num: Int, success: () -> Unit = {}) {
        mStringToOther = jsonObject
        val req = mStringToOther as Request
        requestOK(req, num, success)
    }


    private fun getCommonJs(): JSONObject {
        return JSONObject(mCollarNetwork)
    }

    private fun requestOK(request: Request, num: Int = 3, success: () -> Unit = {}) {
        mOkHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Tools.log("requestOK onFailure $e")
                if (num > 0) {
                    mIoScope.launch {
                        delay(30000)
                        requestOK(request, num - 1, success)
                    }
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string() ?: ""
                Tools.log("onResponse $body")
                val code = response.code
                if (code == 200) {
                    success.invoke()
                } else {
                    if (num > 0) {
                        mIoScope.launch {
                            delay(30000)
                            requestOK(request, num - 1, success)
                        }
                    }
                }
            }
        })
    }

    private val queenStr = "Queen_id_1900"

    fun getNotification(context: Context): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, queenStr)
            .setAutoCancel(false)
            .setSmallIcon(R.drawable.ic_ser_network)
            .setContentTitle("")
    }

}