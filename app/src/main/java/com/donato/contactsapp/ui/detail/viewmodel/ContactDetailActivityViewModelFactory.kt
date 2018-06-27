package com.donato.contactsapp.ui.detail.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * @author Donato Antonini <antoninidonato@gmail.com>
 */
class ContactDetailActivityViewModelFactory @Inject constructor(
        private val contactDetailActivityViewModel: ContactDetailActivityViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactDetailActivityViewModel::class.java)) {
            return contactDetailActivityViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}