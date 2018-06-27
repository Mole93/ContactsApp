package com.donato.contactsapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author Donato Antonini <antoninidonato@gmail.com>
 */
@Parcelize
data class ContactModel(var id: String, var displayName: String, var phoneNumber: String = "", var pictureURI: String = "") : Parcelable