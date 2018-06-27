package com.donato.contactsapp.ui.list.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * @author Donato Antonini <antoninidonato@gmail.com>
 */
class ContactListActivityViewModelFactory @Inject constructor(
        private val contactListViewModel: ContactListViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactListViewModel::class.java)) {
            return contactListViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}