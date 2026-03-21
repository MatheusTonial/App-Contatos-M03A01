package com.tonial.appcontatos.ui.contact.list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonial.appcontatos.data.Contact
import com.tonial.appcontatos.data.ContactDatasource
import com.tonial.appcontatos.data.groupByInitial
import kotlinx.coroutines.launch

class ContactsListViewModel : ViewModel() {

    var uiState: ContactsListUIState by mutableStateOf(ContactsListUIState())
        private set

    init {
        loadContact()
    }

    fun loadContact() {
        uiState = uiState.copy(
            isLoading = true,
            hasError = false,
        )

        viewModelScope.launch {
            uiState = try{
                uiState.copy(
                    isLoading = false,
                    contacts = ContactDatasource.instance.findAll().groupByInitial()
                )
            }
            catch (ex: Exception){
                Log.e("ContactsListViewModel", "loadContact: ", ex)
                uiState.copy(
                    isLoading = false,
                    hasError = true
                )
            }
        }
    }

    fun toggleIsFavorite(contact: Contact) {
        try{
            val updatedContact = contact.copy(isFavorite = !contact.isFavorite)
            ContactDatasource.instance.save(updatedContact)
            uiState = uiState.copy(
                contacts = ContactDatasource.instance.findAll().groupByInitial()
            )
        }
        catch (ex: Exception){
            Log.e("ContactsListViewModel", "toggleIsFavorite: ", ex)
        }
    }
}