package com.tonial.appcontatos.ui.contact.form

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonial.appcontatos.data.ContactDatasource
import com.tonial.appcontatos.data.ContactTypeEnum
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.random.Random

class ContactFormViewModel (
    saveStateHandle: SavedStateHandle
) : ViewModel() {

    private val contactId: Int = saveStateHandle.get<String>("id")?.toIntOrNull() ?: 0

    var uiState: ContactFormState by mutableStateOf(
        ContactFormState(
            contactId = contactId
        )
    )

    init {
        if(!uiState.isNewContatct){
            loadContact()
        }
    }

    fun loadContact() {
        uiState = uiState.copy(
            isLoading = true,
            hasErrorLoading = false
        )
        viewModelScope.launch {
            delay(2000)
            val contact = ContactDatasource.instance.findById(contactId)
            val hasError = Random.nextBoolean()
            uiState = if(contact == null || hasError){
                uiState.copy(
                    isLoading = false,
                    hasErrorLoading = true
                )
            }
            else{
                uiState.copy(
                    isLoading = false,
                    contact = contact,
                    formState = FormState(
                        firstName = FormField(contact.firstName),
                        lastName = FormField(contact.lastName),
                        phoneNumber = FormField(contact.phoneNumber),
                        email = FormField(contact.email),
                        isFavorite = FormField(contact.isFavorite),
                        assetValue = FormField(contact.assetValue.toString()),
                        birthDate = FormField(contact.BirthDate),
                        type = FormField(contact.type)
                    )
                )
            }
        }
    }

    private fun onFirstNameChanged(newValue: String) {
        if (uiState.formState.firstName.value != newValue) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    firstName = FormField(
                        value = newValue,
                        errorMessage = if (newValue.isBlank()) "O nome é obrigatório" else ""
                    )
                )
            )
        }
    }

    private fun onLastNameChanged(newValue: String) {
        if (uiState.formState.lastName.value != newValue) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    lastName = FormField(
                        value = newValue,
                        errorMessage = "" //nao é obrigatorio
                    )
                )
            )
        }
    }

    private fun onPhoneNumberChanged(newValue: String) {
        if (uiState.formState.phoneNumber.value != newValue) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    phoneNumber = FormField(
                        value = newValue,
                        errorMessage = validadePhoneNumber(newValue)
                    )
                )
            )
        }
    }

    private fun validadePhoneNumber(value: String): String {
        if (value.isBlank()) return "O telefone é obrigatório"
        if (value.length !in 10..11) return "O telefone deve conter entre 10 a 11 dígitos"
        if(value.contains(Regex("\\D"))) return "O telefone deve ser apenas números"
        return  ""

    }

    private fun onEmailChanged(newValue: String) {
        if (uiState.formState.email.value != newValue) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    email = FormField(
                        value = newValue,
                        errorMessage = validadeEmail(newValue)
                    )
                )
            )
        }
    }

    private fun validadeEmail(value: String): String {
        if (value.isBlank()) return "O email é obrigatório"
        if (!value.contains("@")) return "email invalido"
        return  ""

    }

    private fun onIsFavoriteChanged(newValue: Boolean) {
        if (uiState.formState.isFavorite.value != newValue) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    isFavorite = FormField(newValue)
                )
            )
        }
    }

    private fun onAssetValueChanged(newValue: String) {
        if (uiState.formState.assetValue.value != newValue) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    assetValue = FormField(
                        value = newValue,
                        errorMessage = validadeAssetValue(newValue)
                    )
                )
            )
        }
    }

    private fun validadeAssetValue(value: String): String {
        if (value.isBlank()) return "O valor patrimonial é obrigatório"
        if(value.contains(Regex("\\D"))) return "O valor patrimonial deve ser apenas números"
        try{
            BigDecimal(value)
            return  ""
        }
        catch (_: NumberFormatException){ //_ pra dizer que nao usa a variavel
            return "O valor patrimonial deve ser um número"
        }
    }

    private fun onBirthDateChanged(newValue: LocalDate) {
        if (uiState.formState.birthDate.value != newValue) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    birthDate = FormField(newValue)
                )
            )
        }
    }

    private fun onTypeChanged(newValue: ContactTypeEnum) {
        if (uiState.formState.type.value != newValue) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    type = FormField(newValue)
                )
            )
        }
    }
}
