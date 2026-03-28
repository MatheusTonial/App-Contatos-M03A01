package com.tonial.appcontatos.ui.contact.form

import com.tonial.appcontatos.data.Contact
import com.tonial.appcontatos.data.ContactTypeEnum
import java.time.LocalDate

data class FormField<T>(
    val value: T,
    val errorMessage: String = ""
){
    val hasError get(): Boolean = errorMessage.isNotBlank()
    val isValid get(): Boolean = !hasError
}

data class FormState(
    val firstName: FormField<String> = FormField(""),
    val lastName: FormField<String> = FormField(""),
    val phoneNumber: FormField<String> = FormField(""),
    val email: FormField<String> = FormField(""),
    val isFavorite: FormField<Boolean> = FormField(false),
    val assetValue: FormField<String> = FormField(""),
    val birthDate: FormField<LocalDate> = FormField(LocalDate.now()),
    val type: FormField<ContactTypeEnum> = FormField(ContactTypeEnum.PERSONAL)
    ){
    val isValid get(): Boolean =
        firstName.isValid &&
        lastName.isValid &&
        phoneNumber.isValid &&
        email.isValid &&
        assetValue.isValid &&
        birthDate.isValid &&
        type.isValid
}

data class ContactFormState(
    val contactId: Int = 0,
    val isLoading: Boolean = false,
    val contact: Contact = Contact(),
    val hasErrorLoading: Boolean = false,
    val formState: FormState = FormState(),
    val isProcessing: Boolean = false,
    val processingErrorMessage: String = "",
    val contactUpdate: Boolean = false,
    val showConfirmationDialog: Boolean = false
){
    val isNewContatct get() : Boolean = contactId <= 0
}

sealed class FormEvent{
    data class UpdateFirstName(val newValue: String) : FormEvent()
    data class UpdateLastName(val newValue: String) : FormEvent()
    data class UpdatePhoneNumber(val newValue: String) : FormEvent()
    data class UpdateEmail(val newValue: String) : FormEvent()
    data class UpdateIsFavorite(val newValue: Boolean) : FormEvent()
    data class UpdateAssetValue(val newValue: String) : FormEvent()
    data class UpdateBirthDate(val newValue: LocalDate) : FormEvent()
    data class UpdateType(val newValue: ContactTypeEnum) : FormEvent()

}