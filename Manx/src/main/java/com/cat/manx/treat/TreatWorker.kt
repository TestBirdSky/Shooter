package com.cat.manx.treat

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

/**
 * Date：2025/7/2
 * Describe:
 */
class TreatWorker(val appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    private val workerHelper by lazy { TreatHelper() }

    override suspend fun doWork(): Result {
        workerHelper.actionTreat(appContext)
        return Result.success()
    }
}