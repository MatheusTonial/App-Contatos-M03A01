package com.tonial.appcontatos.ui.contact.list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonial.appcontatos.data.Contact
import com.tonial.appcontatos.data.ContactDatasource
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
            uiState.value = try{
                uiState.value.copy(
                    isLoading = false,
                    contacts = ContactDatasource.instance.findAll().groupByInitial()
                )
            }
            catch (ex: Exception){
                Log.e("ContactsListViewModel", "loadContact: ", ex)
                uiState.value.copy(
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
            uiState.value = uiState.value.copy(
                contacts = ContactDatasource.instance.findAll().groupByInitial()
            )
        }
        catch (ex: Exception){
            Log.e("ContactsListViewModel", "toggleIsFavorite: ", ex)
        }
    }
}