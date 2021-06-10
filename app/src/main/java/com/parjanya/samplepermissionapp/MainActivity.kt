package com.parjanya.samplepermissionapp

import android.os.Bundle
import com.parjanya.samplepermissionapp.base.BaseActivity
import com.parjanya.samplepermissionapp.contacts.ContactsActivity
import com.parjanya.samplepermissionapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.smsBtn.setOnClickListener { startNewActivity(SmsActivity::class.java) }
        binding.callBtn.setOnClickListener { startNewActivity(PhoneCallActivity::class.java) }
        binding.contactBtn.setOnClickListener { startNewActivity(ContactsActivity::class.java) }
        binding.locationBtn.setOnClickListener { startNewActivity(LocationActivity::class.java) }
        binding.cameraBtn.setOnClickListener { startNewActivity(CameraActivity::class.java) }
    }
}