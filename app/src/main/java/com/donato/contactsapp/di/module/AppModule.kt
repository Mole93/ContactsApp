package com.donato.contactsapp.di.module

import android.app.Application
import android.content.Context
import com.donato.contactsapp.data.provider.ContactsProvider
import com.donato.contactsapp.data.repository.ContactsRepo
import com.donato.contactsapp.data.repository.ContactsRepository

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

/**
 * @author Donato Antonini <antoninidonato@gmail.com>
 */
@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    internal fun provideContactsRepository(contactsProvider: ContactsProvider): ContactsRepository = ContactsRepo(contactsProvider)

    @Provides
    internal fun provideContactsProvider(context: Context): ContactsProvider = ContactsProvider(context)

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

}