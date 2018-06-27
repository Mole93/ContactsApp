package com.donato.contactsapp.di.component

import android.app.Application
import com.donato.contactsapp.ContactsApp
import com.donato.contactsapp.di.builder.ActivityBuilder
import com.donato.contactsapp.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author Donato Antonini <antoninidonato@gmail.com>
 */
@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class), (AppModule::class), (ActivityBuilder::class)])
interface AppComponent : AndroidInjector<ContactsApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

   override fun inject(app: ContactsApp)

}