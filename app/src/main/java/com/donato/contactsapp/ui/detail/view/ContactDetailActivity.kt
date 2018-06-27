package com.donato.contactsapp.ui.detail.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.MenuItem
import com.donato.contactsapp.R
import com.donato.contactsapp.databinding.ActivityContactDetailBinding
import com.donato.contactsapp.ui.detail.viewmodel.ContactDetailActivityViewModel
import com.donato.contactsapp.ui.detail.viewmodel.ContactDetailActivityViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_contact_detail.*
import javax.inject.Inject

/**
 * @author Donato Antonini <antoninidonato@gmail.com>
 */
class ContactDetailActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ContactDetailActivityViewModelFactory

    lateinit var viewModel: ContactDetailActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        viewModel = ViewModelProviders.of(this, factory)[ContactDetailActivityViewModel::class.java]

        viewModel.loadContact(intent.getStringExtra(ARG_ITEM_ID))

        viewModel.contactResult().observe(this, Observer {
            val binding = DataBindingUtil.setContentView<ActivityContactDetailBinding>(this, R.layout.activity_contact_detail)

            binding.contact = it

            initUI(it!!.phoneNumber)
        })

    }

    private fun initUI(mobile: String) {
        setSupportActionBar(detail_toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, R.string.coming_soon, Snackbar.LENGTH_LONG)
                    .show()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragment = ContactDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ContactDetailFragment.ARG_MOBILE, mobile)
            }
        }

        supportFragmentManager.beginTransaction()
                .add(R.id.contact_detail_container, fragment)
                .commit()

    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    finish()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    companion object {
        const val ARG_ITEM_ID = "ARG_ITEM_ID"
    }
}
