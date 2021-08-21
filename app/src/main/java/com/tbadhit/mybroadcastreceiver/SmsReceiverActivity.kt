package com.tbadhit.mybroadcastreceiver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tbadhit.mybroadcastreceiver.databinding.ActivitySmsReceiverBinding

class SmsReceiverActivity : AppCompatActivity() {

    private var binding: ActivitySmsReceiverBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySmsReceiverBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // (1)
        title = getString(R.string.incoming_message)

        // (1)
        binding?.btnClose?.setOnClickListener {
            finish()
        }

        // (1)
        val senderNo = intent.getStringExtra(EXTRA_SMS_NO)
        val senderMessage = intent.getStringExtra(EXTRA_SMS_NO)
        //-----

        // (1)
        binding?.tvFrom?.text = getString(R.string.from, senderNo)
        binding?.tvMessage?.text = senderMessage
        //-----
    }

    // (1)
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    // (1)
    companion object {
        const val EXTRA_SMS_NO = "extra_sms_no"
        const val EXTRA_SMS_MESSAGE = "extra_sms_message"
    }
}