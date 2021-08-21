package com.tbadhit.mybroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log

class SmsReceiver : BroadcastReceiver() {

    // (1)
    override fun onReceive(context: Context, intent: Intent) {
        // (2)
        val bundle = intent.extras
        try {
            if (bundle != null) {
                /*
                Bundle dengan key "pdus" sudah merupakan standar yang digunakan oleh system
                 */
                val pdusObj = bundle.get("pdus") as Array<*>
                for (aPdusObj in pdusObj) {
                    val currentMessage = getIncomingMessage(aPdusObj as Any, bundle)
                    val senderNum = currentMessage.displayOriginatingAddress
                    val message = currentMessage.displayMessageBody
                    Log.d(TAG, "senderNum: $senderNum; message: $message")
                    val showSmsIntent = Intent(context, SmsReceiverActivity::class.java)
                    showSmsIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    showSmsIntent.putExtra(SmsReceiverActivity.EXTRA_SMS_NO, senderNum)
                    showSmsIntent.putExtra(SmsReceiverActivity.EXTRA_SMS_MESSAGE, message)
                    context.startActivity(showSmsIntent)
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "onReceive: $e")
        }
        //------
    }

    // (1)
    // kode untuk menerima data dari pesan yang masuk.
    private fun getIncomingMessage(aObject: Any, bundle: Bundle): SmsMessage {
        val currentSms: SmsMessage
        val format = bundle.getString("format")
        currentSms = if (Build.VERSION.SDK_INT >= 23) {
            SmsMessage.createFromPdu(aObject as ByteArray, format)
        } else SmsMessage.createFromPdu(aObject as ByteArray)
        return currentSms
    }

    // (1)
    companion object {
        private val TAG = SmsReceiver::class.java.simpleName
    }
}