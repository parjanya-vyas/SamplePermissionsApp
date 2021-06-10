package com.parjanya.samplepermissionapp

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.parjanya.samplepermissionapp.base.BaseActivity
import com.parjanya.samplepermissionapp.databinding.ActivityPhoneCallBinding

class PhoneCallActivity : BaseActivity<ActivityPhoneCallBinding>() {

    override val binding by lazy { ActivityPhoneCallBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.makeCallBtn.setOnClickListener { checkPerm(Manifest.permission.CALL_PHONE) }
    }

    override fun onPermissionAllowed() {
        val callIntent = Intent(Intent.ACTION_CALL).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        callIntent.data = Uri.parse("tel:${binding.callToEt.text.toString()}")
        startActivity(callIntent)
    }

    override fun onPermissionDenied() {
        "Must give CALL permission to allow calls".showAsToast(this)
    }
}