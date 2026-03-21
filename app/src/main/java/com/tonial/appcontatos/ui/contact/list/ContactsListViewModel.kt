package com.tonial.appcontatos.ui.contact.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonial.appcontatos.data.Contact
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
                            contacts = listOf(),
                            isLoading = false,
                        )
                    } else {
                        uiState.value.copy(
                            contacts = generateContacts(),
                            isLoading = false,
                        )
                    }
                }
        }
    }

    fun toggleIsFavorite(updatedContact: Contact) {
        uiState.value = uiState.value.copy(
            contacts = uiState.value.contacts.map { currentContact ->
                if(currentContact.id == updatedContact.id){
                    currentContact.copy(isFavorite = !currentContact.isFavorite)
                } else{
                    currentContact
                }
            }
        )
    }
}