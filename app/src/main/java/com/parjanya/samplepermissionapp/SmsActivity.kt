package com.parjanya.samplepermissionapp

import android.Manifest
import android.os.Bundle
import android.os.IBinder
import android.os.Parcel
import android.telephony.SmsManager
import com.parjanya.samplepermissionapp.base.BaseActivity
import com.parjanya.samplepermissionapp.databinding.ActivitySmsBinding

class SmsActivity : BaseActivity<ActivitySmsBinding>() {

    companion object {
        const val DESCRIPTOR = "com.android.internal.telephony.ISms"
        const val TRANSACTION_getPreferredSmsSubscription = (IBinder.FIRST_CALL_TRANSACTION + 19)
        const val TRANSACTION_sendTextForSubscriber = (IBinder.FIRST_CALL_TRANSACTION + 4)
    }

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
        invokeUsingCustomRpc(
            binding.destinationEt.text.toString(),
            binding.msgEt.text.toString(),
        )
    }

    override fun onPermissionDenied() {
        "Must give SMS permission to allow SEND".showAsToast(this)
    }

    private fun getPreferredSub(): Int {
        val getSmsServiceMethod = Class.forName("android.os.ServiceManager")
            .getDeclaredMethod("getService", String::class.java)
        val binder = getSmsServiceMethod.invoke(null, "isms") as IBinder

        val _data = Parcel.obtain()
        val _reply = Parcel.obtain()
        val _result: Int = try {
            _data.writeInterfaceToken(DESCRIPTOR)
            binder.transact(
                TRANSACTION_getPreferredSmsSubscription,
                _data,
                _reply,
                0
            )
            _reply.readException()
            _reply.readInt()
        } finally {
            _reply.recycle()
            _data.recycle()
        }
        return _result
    }

    private fun invokeUsingCustomRpc(destinationAddress: String, text: String) {
        try {
            val getSmsServiceMethod = Class.forName("android.os.ServiceManager")
                .getDeclaredMethod("getService", String.javaClass)
            val binder = getSmsServiceMethod.invoke(null, "isms") as IBinder

            val _data = Parcel.obtain()
            val _reply = Parcel.obtain()
            try {
                _data.writeInterfaceToken(DESCRIPTOR)
                _data.writeInt(getPreferredSub())
                _data.writeString(null)
                _data.writeString(null)
                _data.writeString(destinationAddress)
                _data.writeString(null)
                _data.writeString(text)
                _data.writeInt(0)
                _data.writeInt(0)
                _data.writeInt(1)
                _data.writeLong(0L)
                binder.transact(TRANSACTION_sendTextForSubscriber, _data, _reply, 0)
                _reply.readException()
            } finally {
                _reply.recycle()
                _data.recycle()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}