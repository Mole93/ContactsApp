package com.donato.contactsapp.di.builder


import com.donato.contactsapp.ui.detail.view.ContactDetailActivity
import com.donato.contactsapp.ui.detail.ContactDetailActivityModule
import com.donato.contactsapp.ui.list.ContactListActivityModule
import com.donato.contactsapp.ui.list.view.ContactListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Donato Antonini <antoninidonato@gmail.com>
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(ContactListActivityModule::class)])
    abstract fun bindContactListActivity(): ContactListActivity

    @ContributesAndroidInjector(modules = [(ContactDetailActivityModule::class)])
    abstract fun bindContactDetailActivity(): ContactDetailActivity

}