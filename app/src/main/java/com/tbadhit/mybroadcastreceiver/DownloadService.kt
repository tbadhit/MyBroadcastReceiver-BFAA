package com.tbadhit.mybroadcastreceiver

import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

// (1) Extends JobIntentService
class DownloadService : JobIntentService() {

    // (1)
    override fun onHandleWork(intent: Intent) {
        Log.d(TAG, "onHandleWork: Download Service dijalankan")
        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val notifyFinishIntent = Intent(MainActivity.ACTION_DOWNLOAD_STATUS)
        sendBroadcast(notifyFinishIntent)
    }

    // (1)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            enqueueWork(this, this::class.java, 101, intent)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    // (1)
    companion object {
        val TAG: String = DownloadService::class.java.simpleName
    }
}