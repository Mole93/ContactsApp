package com.donato.contactsapp.ui.list.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.donato.contactsapp.data.repository.ContactsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Donato Antonini <antoninidonato@gmail.com>
 */
class ContactListViewModel @Inject constructor(private var contactsRepo: ContactsRepository) : ViewModel() {

    @Inject
    lateinit var compositeDisposable: CompositeDisposable
    private var contactsResult: MutableLiveData<List<ContactItemViewModel>> = MutableLiveData()
    private var contactsError: MutableLiveData<String> = MutableLiveData()


    fun contactsResult(): LiveData<List<ContactItemViewModel>> = contactsResult

    fun contactsError(): LiveData<String> = contactsError


    fun loadContacts() {


        compositeDisposable.add(contactsRepo.getContacts()
                .subscribeOn(Schedulers.newThread())
                .map {
                    val list = ArrayList<ContactItemViewModel>()
                    for (contact in it) {
                        val item = ContactItemViewModel(contact.displayName, contact.phoneNumber, contact.pictureURI)
                        item.id = contact.id
                        list.add(item)
                    }
                    list
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ contactsResult.postValue(it) }, { e ->
                    contactsError.postValue(e.message)
                }))


    }

    fun dispose() {
        compositeDisposable.clear()
    }

}