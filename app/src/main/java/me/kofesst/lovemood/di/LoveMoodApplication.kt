package me.kofesst.lovemood.di

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.kofesst.lovemood.widgets.RelationshipWidgetWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class LoveMoodApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        startWorkerTask()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(Log.ERROR)
            .build()
    }

    private fun startWorkerTask() {
        CoroutineScope(Dispatchers.Default).launch {
            scheduleWorkerTask()
        }
    }

    private fun scheduleWorkerTask() {
        val taskRequest = PeriodicWorkRequest.Builder(
            RelationshipWidgetWorker::class.java,
            15L,
            TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            RelationshipWidgetWorker.WORKER_TAG,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            taskRequest
        )
    }
}