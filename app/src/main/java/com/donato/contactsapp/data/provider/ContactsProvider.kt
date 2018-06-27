package com.donato.contactsapp.data.provider

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import com.donato.contactsapp.data.model.ContactModel
import io.reactivex.Observable
import javax.inject.Inject


/**
 * @author Donato Antonini <antoninidonato@gmail.com>
 */
class ContactsProvider @Inject constructor(var context: Context) {

    private var contentResolver: ContentResolver = context.contentResolver


    fun getContacts(): Observable<List<ContactModel>> {
        return Observable.create<List<ContactModel>> {
            val list = ArrayList<ContactModel>()

            val cur = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC")

            if (cur!!.getCount() > 0) {
                while (cur.moveToNext()) {
                    val contactModel = createContactModel(cur)
                    if (contactModel.phoneNumber != "")
                        list.add(contactModel)
                }
            }

            cur.close()
            it.onNext(list)
            it.onComplete()

        }

    }

    fun getContact(id: String): Observable<ContactModel> {

        return Observable.create({
            val cur = contentResolver.query(
                    ContactsContract.Data.CONTENT_URI, null,
                    ContactsContract.Data.CONTACT_ID + "=" + id, null, null)

            if (cur.moveToFirst()) {
                val contactModel = createContactModel(cur)
                it.onNext(contactModel)
                it.onComplete()
            }
            cur.close()
        })

    }

    private fun createContactModel(cur: Cursor): ContactModel {
        val id: String =
                if (cur.columnNames.contains(ContactsContract.Data.CONTACT_ID))
                    cur.getString(cur.getColumnIndex(ContactsContract.Data.CONTACT_ID))
                else cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID))

        val name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
        val pictureUri = getPhotoUri(id).toString()
        val contactModel = ContactModel(id = id, displayName = name, pictureURI = pictureUri)
        if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
            contactModel.phoneNumber = getPhoneNumber(id)
        }

        return contactModel
    }

    private fun getPhotoUri(id: String): Uri? {
        val cur = contentResolver.query(
                ContactsContract.Data.CONTENT_URI, null,
                ContactsContract.Data.CONTACT_ID + "=" + id + " AND "
                        + ContactsContract.Data.MIMETYPE + "='"
                        + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'", null, null)
        if (cur != null) {
            if (!cur.moveToFirst()) {
                cur.close()
                return null // no photo
            }
            cur.close()
        } else {
            return null // error in cursor process
        }
        cur.close()

        val person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, java.lang.Long
                .parseLong(id))
        return Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY)
    }


    private fun getPhoneNumber(id: String): String {
        var phoneNo = ""
        val pCur = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                arrayOf(id), null)

        while (pCur!!.moveToNext()) {
            phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
        }
        pCur.close()

        return phoneNo
    }


}