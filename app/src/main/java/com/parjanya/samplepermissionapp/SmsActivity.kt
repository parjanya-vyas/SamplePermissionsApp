package com.parjanya.samplepermissionapp

import android.Manifest
import android.os.Bundle
import android.telephony.SmsManager
import com.parjanya.samplepermissionapp.base.BaseActivity
import com.parjanya.samplepermissionapp.databinding.ActivitySmsBinding

class SmsActivity : BaseActivity<ActivitySmsBinding>() {

    override val binding by lazy { ActivitySmsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.sendBtn.setOnClickListener { checkPerm(Manifest.permission.SEND_SMS) }
    }

    override fun onPermissionAllowed() {
        SmsManager.getDefault()
            .sendTextMessage(
                binding.destinationEt.text.toString(),
                null,
                binding.msgEt.text.toString(),
                null,
                null
            )
    }

    override fun onPermissionDenied() {
        "Must give SMS permission to allow SEND".showAsToast(this)
    }

}