package com.parjanya.samplepermissionapp.base

import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher

interface IPermissionActivity {

    val permissionLauncher: ActivityResultLauncher<String>

    fun onPermissionAllowed()

    fun onPermissionDenied()

    //Will be automatically overridden if used with base activity class
    fun shouldShowRequestPermissionRationale(perm: String): Boolean

    //Will be automatically overridden if used with base activity class
    fun checkSelfPermission(perm: String): Int

    private fun requestPermission(perm: String) {
        if (shouldShowRequestPermissionRationale(perm))
            onPermissionDenied()
        else
            permissionLauncher.launch(perm)
    }

    fun checkPerm(perm: String) {
        if (PackageManager.PERMISSION_GRANTED == checkSelfPermission(perm))
            onPermissionAllowed()
        else
            requestPermission(perm)
    }
}