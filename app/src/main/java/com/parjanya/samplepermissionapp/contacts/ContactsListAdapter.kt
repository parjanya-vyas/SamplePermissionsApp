package com.parjanya.samplepermissionapp.contacts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.parjanya.samplepermissionapp.databinding.ContactListItemBinding

class ContactsListAdapter(context: Context, private val contactList: ArrayList<Pair<String, String>>)
    : RecyclerView.Adapter<ContactsListAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    class ViewHolder(val binding: ContactListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ContactListItemBinding.inflate(inflater))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.nameTv.text = contactList[position].first
        holder.binding.numberTv.text = contactList[position].second
    }

    override fun getItemCount(): Int = contactList.size

}