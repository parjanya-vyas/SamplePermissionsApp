package com.parjanya.samplepermissionapp.base

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity(), IPermissionActivity {

    abstract val binding: T

    override val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) onPermissionAllowed() else onPermissionDenied()
    }

    fun startNewActivity(activityClass: Class<*>) {
        startActivity(Intent(this, activityClass))
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    private fun requestPermission(perm: String) {
        if (shouldShowRequestPermissionRationale(perm))
            onPermissionDenied()
        else
            permissionLauncher.launch(perm)
    }

    override fun onPermissionAllowed() {
        //override in child if uses permission
    }

    override fun onPermissionDenied() {
        //override in child if uses permission
    }
}