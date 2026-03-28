package com.tonial.appcontatos.ui.contact.form

import com.tonial.appcontatos.data.Contact

data class ContactFormState(
    val contactId: Int = 0,
    val isLoading: Boolean = false,
    val contact: Contact = Contact(),
    val hasErrorLoading: Boolean = false,
){
    val isNewContatct get() : Boolean = contactId <= 0
}