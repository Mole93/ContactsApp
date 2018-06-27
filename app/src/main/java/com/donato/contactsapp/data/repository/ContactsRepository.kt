package com.donato.contactsapp.data.repository

import com.donato.contactsapp.data.model.ContactModel
import io.reactivex.Observable

/**
 * @author Donato Antonini <antoninidonato@gmail.com>
 */
interface ContactsRepository {

    fun getContacts() : Observable<List<ContactModel>>

    fun getContact(id : String) : Observable<ContactModel>

}