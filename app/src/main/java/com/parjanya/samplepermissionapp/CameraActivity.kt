package com.parjanya.samplepermissionapp

import android.Manifest
import android.os.Bundle
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.parjanya.samplepermissionapp.base.BaseActivity
import com.parjanya.samplepermissionapp.databinding.ActivityCameraBinding

class CameraActivity : BaseActivity<ActivityCameraBinding>() {

    override val binding by lazy { ActivityCameraBinding.inflate(layoutInflater) }
    private val cameraProviderFuture by lazy { ProcessCameraProvider.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPerm(Manifest.permission.CAMERA)
    }

    override fun onPermissionAllowed() {
        cameraProviderFuture.addListener(Runnable {
            val preview = Preview.Builder().build()
            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()
            preview.setSurfaceProvider(binding.cameraPv.surfaceProvider)
            cameraProviderFuture.get()
                .bindToLifecycle(this as LifecycleOwner, cameraSelector, preview)
        }, ContextCompat.getMainExecutor(this))
    }

    override fun onPermissionDenied() {
        "Must provide CAMERA permission to use camera".showAsToast(this)
    }
}