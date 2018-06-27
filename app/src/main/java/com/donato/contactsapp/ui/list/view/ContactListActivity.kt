package com.donato.contactsapp.ui.list.view

import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.donato.contactsapp.R
import com.donato.contactsapp.ui.list.viewmodel.ContactItemViewModel
import com.donato.contactsapp.ui.list.viewmodel.ContactListActivityViewModelFactory
import com.donato.contactsapp.ui.list.viewmodel.ContactListViewModel
import com.donato.contactsapp.utils.ContactPermission
import com.donato.contactsapp.utils.Utils
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_contact_list.*
import kotlinx.android.synthetic.main.contact_list.*
import javax.inject.Inject


class ContactListActivity : DaggerAppCompatActivity(), ContactPermission.Callback {

    @Inject
    lateinit var contactListActivityViewModelFactory: ContactListActivityViewModelFactory

    lateinit var contactPermission: ContactPermission
    private var twoPane: Boolean = false

    lateinit var contactListViewModel: ContactListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        contactListViewModel = ViewModelProviders.of(this, contactListActivityViewModelFactory)[ContactListViewModel::class.java]
        contactPermission = ContactPermission(this, this)
        contactPermission.checkPermission()

        fab.setOnClickListener { view ->
            Snackbar.make(view, R.string.coming_soon, Snackbar.LENGTH_LONG)
                    .show()
        }

        if (contact_detail_container != null) {
            twoPane = true
        }

    }

    private fun setupRecyclerView(items: List<ContactItemViewModel>) {
        progress_bar.visibility = View.GONE
        contact_list.visibility = View.VISIBLE
        contact_list.adapter = ContactsAdapter(this, items, twoPane)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {

        contactPermission.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    override fun onDestroy() {
        super.onDestroy()
        contactListViewModel.dispose()
    }

    override fun contactsPermissionDenied() {
        Utils.showConfirmationDialog(this, R.string.no_permission_denied, R.string.no_permission_denied_message, R.string.ok)

    }

    override fun contactsPermissionRationale() {
        Utils.showConfirmationDialog(this, R.string.no_permission, R.string.no_permission_message, R.string.ok,
                DialogInterface.OnClickListener { dialog, which -> contactPermission.requestPermission() })
    }

    override fun hasContactsPermission() {
        progress_bar.visibility = View.VISIBLE

        contactListViewModel.loadContacts()
        contactListViewModel.contactsResult().observe(this, android.arch.lifecycle.Observer {
            val list = it.orEmpty()

            if (list.isEmpty()) {
                progress_bar.visibility = View.GONE
                Utils.showConfirmationDialog(this, R.string.no_contacts, R.string.no_contacts_message, R.string.ok)
            } else {
                setupRecyclerView(list)
            }

        })


    }

}
