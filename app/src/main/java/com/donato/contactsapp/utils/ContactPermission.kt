package com.donato.contactsapp.utils

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity

/**
 * @author Donato Antonini <antoninidonato@gmail.com>
 */
class ContactPermission(private var callback: Callback, private var activityCompat: AppCompatActivity) {

    private val PERMISSIONS_REQUEST_READ_CONTACTS = 200



    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(activityCompat,
                        Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activityCompat,
                            Manifest.permission.READ_CONTACTS)) {
                callback.contactsPermissionRationale()
            } else {
                requestPermission()
            }
        } else {
            callback.hasContactsPermission()

        }
    }

    fun requestPermission() {
        ActivityCompat.requestPermissions(activityCompat,
                arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CALENDAR),
                PERMISSIONS_REQUEST_READ_CONTACTS)
    }

    fun onRequestPermissionsResult(requestCode: Int,
                                   permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                callback.hasContactsPermission()
            } else {
                callback.contactsPermissionDenied()
            }
        }
    }


    interface Callback {
        fun hasContactsPermission()
        fun contactsPermissionDenied()
        fun contactsPermissionRationale()
    }

}