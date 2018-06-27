package com.donato.contactsapp.data.repository


import com.donato.contactsapp.data.model.ContactModel
import com.donato.contactsapp.data.provider.ContactsProvider
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Donato Antonini <antoninidonato@gmail.com>
 */
class ContactsRepo @Inject constructor(private val contactsProvider: ContactsProvider) : ContactsRepository {

    override fun getContacts(): Observable<List<ContactModel>> = contactsProvider.getContacts()

    override fun getContact(id: String): Observable<ContactModel> = contactsProvider.getContact(id)


}