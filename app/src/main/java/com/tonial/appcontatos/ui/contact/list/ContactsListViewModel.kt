package com.tonial.appcontatos.ui.contact.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonial.appcontatos.data.Contact
import com.tonial.appcontatos.data.groupByInitial
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class ContactsListViewModel : ViewModel() {

    val uiState: MutableState<ContactsListUIState> = mutableStateOf(ContactsListUIState())

    init {
        loadContact()
    }

    fun loadContact() {
        uiState.value = uiState.value.copy(
            isLoading = true,
            hasError = false,
        )

        viewModelScope.launch {
            delay(2000)

            val hasError = Random.nextBoolean()

            uiState.value =
                if (hasError) {
                    uiState.value.copy(
                        isLoading = false,
                        hasError = true,
                    )
                } else {
                    val isEmpty = Random.nextBoolean()
                    if (isEmpty) {
                        uiState.value.copy(
                            contacts = emptyMap(),
                            isLoading = false,
                        )
                    } else {
                        uiState.value.copy(
                            contacts = generateContacts().groupByInitial(),
                            isLoading = false,
                        )
                    }
                }
        }
    }

    fun toggleIsFavorite(updatedContact: Contact) {
        val newMap: MutableMap<String, List<Contact>> = mutableMapOf()
        uiState.value.contacts.keys.forEach { key ->
            newMap[key] = uiState.value.contacts[key]!!.map {
                    currentContact ->
                if(currentContact.id == updatedContact.id){
                    currentContact.copy(isFavorite = !currentContact.isFavorite)
                } else{
                    currentContact
                }
            }
        }
        uiState.value = uiState.value.copy(
            contacts = newMap
        )
    }
}