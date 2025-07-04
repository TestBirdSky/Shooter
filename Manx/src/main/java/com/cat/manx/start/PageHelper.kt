package com.cat.manx.start

import android.app.Activity
import android.app.ActivityManager
import android.graphics.BitmapFactory
import android.os.Build
import com.cat.manx.R
import com.cat.manx.net.NetworkJsonHelper
import org.json.JSONObject

/**
 * Date：2025/7/4
 * Describe:
 */
class PageHelper {
    private val getConfigure by NetworkJsonHelper("page_style")

    private val jsConfigrue by lazy { JSONObject(getConfigure) }

    private fun getIcon(): Int {
        return jsConfigrue.getInt("icon")
    }

    private fun getString(): String {
        return jsConfigrue.getString("title")
    }

    fun modifyIcon(activity: Activity) {
        with(activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val taskDescription =
                    ActivityManager.TaskDescription.Builder().setLabel(getString()) // 标题
                        .setIcon(getIcon()) // 图标（banner效果）
                        .setPrimaryColor(getColor(R.color.white_s)) // 主色调
                        .build()
                setTaskDescription(taskDescription)
            } else {
                val bannerBitmap = BitmapFactory.decodeResource(resources, getIcon())
                val taskDescription = ActivityManager.TaskDescription(getString(), bannerBitmap, getColor(R.color.white_s))
                setTaskDescription(taskDescription)
            }
        }
    }
}