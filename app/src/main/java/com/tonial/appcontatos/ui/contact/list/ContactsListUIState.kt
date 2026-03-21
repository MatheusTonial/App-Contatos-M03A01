package com.tonial.appcontatos.ui.contact.list

import com.tonial.appcontatos.data.Contact

data class ContactsListUIState (
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val contacts: Map<String, List<Contact>> = emptyMap()
)