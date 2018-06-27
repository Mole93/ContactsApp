package com.donato.contactsapp.ui.detail.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.donato.contactsapp.data.repository.ContactsRepository
import com.donato.contactsapp.ui.list.viewmodel.ContactItemViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Donato Antonini <antoninidonato@gmail.com>
 */
class ContactDetailActivityViewModel @Inject constructor(private var contactsRepo: ContactsRepository) : ViewModel()  {

    @Inject
    lateinit var compositeDisposable: CompositeDisposable
    private var contactResult: MutableLiveData<ContactItemViewModel> = MutableLiveData()
    private var contactError: MutableLiveData<String> = MutableLiveData()

    fun contactResult(): LiveData<ContactItemViewModel> = contactResult

    fun contactError(): LiveData<String> = contactError


    fun loadContact(id : String) {


        compositeDisposable.add(contactsRepo.getContact(id)
                .subscribeOn(Schedulers.newThread())
                .map {
                   val contact =  ContactItemViewModel(it.displayName, it.phoneNumber, it.pictureURI)
                    contact.id = id

                    contact
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ contactResult.postValue(it) }, { e ->
                    contactError.postValue(e.message)
                }))


    }

    fun dispose() {
        compositeDisposable.clear()
    }
}