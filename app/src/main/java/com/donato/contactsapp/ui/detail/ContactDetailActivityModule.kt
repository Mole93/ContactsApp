package com.donato.contactsapp.ui.detail

import android.arch.lifecycle.ViewModelProvider

import com.donato.contactsapp.ui.list.viewmodel.ContactListActivityViewModelFactory

import dagger.Module
import dagger.Provides

/**
 * @author Donato Antonini <antoninidonato@gmail.com>
 */
@Module
class ContactDetailActivityModule {

    @Provides
    internal fun provideContactListActivityViewModelFactory(
            factory: ContactListActivityViewModelFactory): ViewModelProvider.Factory = factory

}