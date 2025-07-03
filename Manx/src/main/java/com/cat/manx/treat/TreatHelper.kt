package com.cat.manx.treat

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.cat.manx.feline.FelineWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * Date：2025/7/2
 * Describe:
 */
class TreatHelper {
    private val mIoScope by lazy { CoroutineScope(Dispatchers.IO) }

    fun actionTreat(context: Context): WorkManager {
        val workRequest = OneTimeWorkRequest.Builder(TreatWorker::class.java)
            .setInitialDelay(49, TimeUnit.SECONDS).build()
        val workManager = WorkManager.getInstance(context)
        workManager.enqueueUniqueWork("treat_work_cloak", ExistingWorkPolicy.KEEP, workRequest)
        return workManager
    }

    fun first(context: Context) {
        mIoScope.launch {
            delay(200)
            val workManager = actionTreat(context)
            delay(300)
            val work =
                PeriodicWorkRequest.Builder(FelineWorker::class.java, 15, TimeUnit.MINUTES)
                    .build()
            workManager.enqueueUniquePeriodicWork(
                "Feline_worker_tips",
                ExistingPeriodicWorkPolicy.UPDATE,
                work
            )
        }
    }
}