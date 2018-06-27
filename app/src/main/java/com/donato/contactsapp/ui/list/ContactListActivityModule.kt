package com.donato.contactsapp.ui.list

import android.arch.lifecycle.ViewModelProvider

import com.donato.contactsapp.ui.list.viewmodel.ContactListActivityViewModelFactory

import dagger.Module
import dagger.Provides

/**
 * @author Donato Antonini <antoninidonato@gmail.com>
 */
@Module
class ContactListActivityModule {

    @Provides
    internal fun provideContactListActivityViewModelFactory(
            factory: ContactListActivityViewModelFactory): ViewModelProvider.Factory = factory

}