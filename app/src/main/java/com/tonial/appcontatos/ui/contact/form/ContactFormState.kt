package com.tonial.appcontatos.ui.contact.form

import com.tonial.appcontatos.data.Contact
import com.tonial.appcontatos.data.ContactTypeEnum
import java.time.LocalDate

data class FromField<T>(
    val value: T,
    val errorMessage: String = ""
)

data class FrormState(
    val firstName: FromField<String> = FromField(""),
    val lastName: FromField<String> = FromField(""),
    val phoneNumber: FromField<String> = FromField(""),
    val email: FromField<String> = FromField(""),
    val isFavorite: FromField<Boolean> = FromField(false),
    val assetValue: FromField<String> = FromField(""),
    val birthDate: FromField<LocalDate> = FromField(LocalDate.now()),
    val type: FromField<ContactTypeEnum> = FromField(ContactTypeEnum.PERSONAL)
    )

data class ContactFormState(
    val contactId: Int = 0,
    val isLoading: Boolean = false,
    val contact: Contact = Contact(),
    val hasErrorLoading: Boolean = false,
    val formState: FrormState = FrormState()
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