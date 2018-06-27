package com.donato.contactsapp.ui.detail.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.donato.contactsapp.R
import kotlinx.android.synthetic.main.contact_detail.view.*

/**
 * @author Donato Antonini <antoninidonato@gmail.com>
 */
class ContactDetailFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.contact_detail, container, false)

        rootView.let {

            it.detail_mobile.text = arguments?.getString(ARG_MOBILE)
        }

        return rootView
    }

    companion object {
        const val ARG_MOBILE = "ARG_MOBILE"
    }
}
