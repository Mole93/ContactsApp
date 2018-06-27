package com.donato.contactsapp.ui.list.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.BindingAdapter
import android.widget.ImageView
import com.donato.contactsapp.BR
import com.donato.contactsapp.R
import com.donato.contactsapp.utils.GlideApp


/**
 * @author Donato Antonini <antoninidonato@gmail.com>
 */
class ContactItemViewModel(displayName: String, phoneNumber: String, pictureURI: String) : BaseObservable() {

    @Bindable
    var displayName: String = displayName
        set(value) {
            notifyPropertyChanged(BR.displayName)
        }
    @Bindable
    var phoneNumber: String = phoneNumber
        set(value) {
            notifyPropertyChanged(BR.phoneNumber)
        }
    @Bindable
    var pictureURI: String = pictureURI
        set(value) {
            notifyPropertyChanged(BR.pictureURI)
        }

    lateinit var id: String


    companion object {
        @BindingAdapter("imageUrl")
        @JvmStatic
        fun loadImage(view: ImageView, imageUrl: String) {

            GlideApp.with(view.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(view)

        }
    }


}