package com.tbadhit.mybroadcastreceiver

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.tbadhit.mybroadcastreceiver.databinding.ActivityMainBinding

// Teori :
// Broadcast Receiver adalah cara sederhana untuk menindaklanjuti sebuah broadcast message yang dipancarkan oleh dirinya sendiri, aplikasi lain atau sistem Android
// Broadcast Message adalah pesan yang akan dipancarkan melalui obyek Intent.

// Codelab (BroadcastReceiver from SMS Event) :
// Update "activity_main.xml"
// add code "MainActivity" (1)
// Create new activity "SmsReceiverActivity"
// update "activity_sms_receiver.xml"
// add style "style.xml" (1)
// update style "AndroidManifest" (1) "android:theme="@style/Theme.Broadcast.SmsReceiver""
// add code "SmsReceiverActivity" (1)
// Create new receiver class "SmsReceiver" ( New → Other → BroadcastReceiver )
// add code "SmsReceiver" (1) (2)
// add permission "AndroidManifest" (2)
// create permission (for android 9++):
// create new kotlin object class "PermissionHandler" + add code
// call method "check" on "MainActivity" (2)

// Codelab (BroadcastReceiver from Custom Event) :
// add code "activtiy_main.xml" (Button Download File)
// add code "MainActivity" (3)
// create new kotlin class "DownloadService"
// add code "DownloadService" (1)
// add code "AndroidManifest" (3)
// add code "MainActivity" (4)

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var binding: ActivityMainBinding? = null

    // (4)
    private lateinit var downloadReceiver: BroadcastReceiver
    //-----

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // (1)
        binding?.btnPermission?.setOnClickListener(this)

        // (3)
        binding?.btnDownload?.setOnClickListener(this)
        //-----

        // (4)
        downloadReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d(DownloadService.TAG, "onReceive: Download Selesai")
                Toast.makeText(context, "Download Selesai", Toast.LENGTH_SHORT).show()
            }
        }

        // (4)
        val downloadIntentFilter = IntentFilter(ACTION_DOWNLOAD_STATUS)
        registerReceiver(downloadReceiver, downloadIntentFilter)
        //-----
    }

    // (1)
    override fun onClick(v: View?) {
        // (2)
        when (v?.id) {
            R.id.btn_permission -> PermissionManager.check(this, Manifest.permission.RECEIVE_SMS,
                SMS_REQUEST_CODE)

            // (3)
            R.id.btn_download -> {
                // (4)
                val downloadServiceIntent = Intent(this, DownloadService::class.java)
                startService(downloadServiceIntent)
                //-----
            }
            //----
        }
        //-----
    }

    // (2)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SMS_REQUEST_CODE) {
            when (PackageManager.PERMISSION_GRANTED) {
                grantResults[0] -> Toast.makeText(this, "Sms receiver permission diterima", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, "Sms receiver permission ditolak", Toast.LENGTH_SHORT).show()
            }
        }
    }
    //----------

    // (1)
    override fun onDestroy() {
        super.onDestroy()
        // (4)
        unregisterReceiver(downloadReceiver)
        //-----
        binding = null
    }

    companion object {
        // (3)
        const val ACTION_DOWNLOAD_STATUS = "download_status"
        //-----
        private const val SMS_REQUEST_CODE = 101
    }
}