package com.donato.contactsapp.ui.list.view

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.donato.contactsapp.R
import com.donato.contactsapp.databinding.ItemContactListBinding
import com.donato.contactsapp.ui.detail.view.ContactDetailActivity
import com.donato.contactsapp.ui.detail.view.ContactDetailFragment
import com.donato.contactsapp.ui.list.viewmodel.ContactItemViewModel

/**
 * @author Donato Antonini <antoninidonato@gmail.com>
 */
class ContactsAdapter(private val parentActivity: ContactListActivity,
                      private val values: List<ContactItemViewModel>,
                      private val twoPane: Boolean) :
        RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as ContactItemViewModel
            if (twoPane) {
                val fragment = ContactDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ContactDetailFragment.ARG_MOBILE, item.phoneNumber)
                    }
                }
                parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.contact_detail_container, fragment)
                        .commit()
            } else {
                val intent = Intent(v.context, ContactDetailActivity::class.java).apply {
                    putExtra(ContactDetailActivity.ARG_ITEM_ID, item.id)
                }
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)


        val view = DataBindingUtil.inflate<ItemContactListBinding>(layoutInflater, R.layout.item_contact_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.binding.contact = item


        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(itemBinding: ItemContactListBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        val binding: ItemContactListBinding = itemBinding

    }
}