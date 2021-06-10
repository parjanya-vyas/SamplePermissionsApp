package com.parjanya.samplepermissionapp.contacts

import android.Manifest
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.parjanya.samplepermissionapp.base.BaseActivity
import com.parjanya.samplepermissionapp.databinding.ActivityContactsBinding
import com.parjanya.samplepermissionapp.showAsToast

class ContactsActivity : BaseActivity<ActivityContactsBinding>(), LoaderManager.LoaderCallbacks<Cursor> {

    override val binding by lazy { ActivityContactsBinding.inflate(layoutInflater) }

    private val contactsList: ArrayList<Pair<String, String>> = ArrayList()

    companion object {
        private const val LOADER_ID = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPerm(Manifest.permission.READ_CONTACTS)
    }

    override fun onPermissionAllowed() {
        LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this)
    }

    override fun onPermissionDenied() {
        "Cannot show contacts without CONTACTS permission".showAsToast(this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            applicationContext,
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER),
            null,
            null,
            null
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if (data != null && data.count > 0) {
            data.moveToFirst()
            do {
                val contact = Pair(
                    data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                    data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                )
                if (!contact.first.isNullOrBlank() && !contact.second.isNullOrBlank())
                    contactsList.add(contact)
            } while (data.moveToNext())
        }
        binding.progressBar.visibility = View.GONE
        binding.contactsRv.visibility = View.VISIBLE
        initRecyclerView()
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        //Do nothing
    }

    private fun initRecyclerView() {
        binding.contactsRv.layoutManager = LinearLayoutManager(this)
        binding.contactsRv.adapter = ContactsListAdapter(this, contactsList)
    }
}