package com.tonial.appcontatos.ui.contact.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tonial.appcontatos.data.Contact
import com.tonial.appcontatos.data.ContactTypeEnum
import com.tonial.appcontatos.ui.contact.form.composables.FormCheckbox
import com.tonial.appcontatos.ui.contact.form.composables.FormDatePicker
import com.tonial.appcontatos.ui.contact.form.composables.FormFieldRow
import com.tonial.appcontatos.ui.contact.form.composables.FormRadioButton
import com.tonial.appcontatos.ui.contact.form.composables.FormTextField
import com.tonial.appcontatos.ui.shared.composables.ContactAvatar
import com.tonial.appcontatos.ui.shared.composables.DefaultErrorState
import com.tonial.appcontatos.ui.shared.composables.DefaultLoadingState
import com.tonial.appcontatos.ui.theme.AppContatosTheme

@Composable
fun ContactFormScreen(
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit,
    viewModel: ContactFormViewModel = viewModel()
) {
    val contentModifier: Modifier = modifier.fillMaxSize()
    if (viewModel.uiState.isLoading) {
        DefaultLoadingState(modifier = contentModifier)
    } else if (viewModel.uiState.hasErrorLoading) {
        DefaultErrorState(
            modifier = contentModifier,
            onTryAgainPress = viewModel::loadContact
        )
    } else {
        Scaffold(
            modifier = contentModifier,
            topBar = {
                AppBar(
                    isNewContact = true,
                    onBackPress = onBackPress
                )
            }
        ) { paddingValues ->
            FormContent(
                modifier = Modifier.padding(paddingValues),
                contact = viewModel.uiState.contact
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    isNewContact: Boolean,
    onBackPress: () -> Unit,
) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = {
            Text(if (isNewContact) "Novo contato" else "Editar contato")
        },
        navigationIcon = {
            IconButton(onClick = onBackPress) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar"
                )
            }
        }
    )
}

private class BooleanParameterProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean> = sequenceOf(true, false)
}

@Composable
@Preview(showBackground = true)
private fun AppBarPreview(
    @PreviewParameter(BooleanParameterProvider::class) isNewContact: Boolean
) {
    AppContatosTheme {
        AppBar(
            isNewContact = isNewContact,
            onBackPress = {},
        )
    }
}

@Composable
private fun FormContent(
    modifier: Modifier = Modifier,
    contact: Contact
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val formTextFieldModifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
        ContactAvatar(
            modifier = Modifier.padding(16.dp),
            firstName = contact.firstName,
            lastName = contact.lastName,
            size = 150.dp,
            textStyle = MaterialTheme.typography.displayLarge
        )
        FormFieldRow(
            label = "Nome",
            imageVector = Icons.Filled.Person
        ) {
            FormTextField(
                modifier = formTextFieldModifier,
                label = "Nome",
                value = contact.firstName,
                onValueChange = {},
                keyboardCapitalization = KeyboardCapitalization.Words
            )
        }
        FormFieldRow(
            label = "Sobrenome"
        ) {
            FormTextField(
                modifier = formTextFieldModifier,
                label = "Sobrenome",
                value = contact.lastName,
                onValueChange = {},
                keyboardCapitalization = KeyboardCapitalization.Words
            )
        }
        FormFieldRow(
            label = "Telefone",
            imageVector = Icons.Filled.Phone
        ) {
            FormTextField(
                modifier = formTextFieldModifier,
                label = "Telefone",
                value = contact.phoneNumber,
                onValueChange = {},
                keyboardType = KeyboardType.Phone
            )
        }
        FormFieldRow(
            label = "E-mail",
            imageVector = Icons.Filled.Mail
        ) {
            FormTextField(
                modifier = formTextFieldModifier,
                label = "E-mail",
                value = contact.email,
                onValueChange = {},
                keyboardType = KeyboardType.Email
            )
        }
        FormFieldRow(
            label = "Data de aniversário"
        ) {
            FormDatePicker(
                modifier = formTextFieldModifier,
                label = "Data de aniversário",
                value = contact.BirthDate,
                onValueChange = {}
            )
        }
        FormFieldRow(
            label = "Valor patrimonial",
            imageVector = Icons.Filled.AttachMoney
        ) {
            FormTextField(
                modifier = formTextFieldModifier,
                label = "Valor patrimonial",
                value = contact.assetValue.toString(),
                onValueChange = {},
                keyboardType = KeyboardType.Number
            )
        }
        val choiceOptionsModifier = Modifier.padding(8.dp)
        FormFieldRow(
            label = "Favorito"
        ) {
            FormCheckbox(
                modifier = choiceOptionsModifier,
                label = "Favorito",
                checked = contact.isFavorite,
                onCheckChanged = {}
            )
        }
        FormFieldRow(
            label = "Tipo"
        ) {
            FormRadioButton(
                modifier = choiceOptionsModifier,
                label = "Pessoal",
                value = ContactTypeEnum.PERSONAL,
                groupValue = contact.type,
                onValueChanged = {}
            )
            FormRadioButton(
                modifier = choiceOptionsModifier,
                label = "Profissional",
                value = ContactTypeEnum.PROFESSIONAL,
                groupValue = contact.type,
                onValueChanged = {}
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ContactFormScreenPreview() {

}

@Preview(showBackground = true)
@Composable
fun FormContentPreview(modifier: Modifier = Modifier) {
    AppContatosTheme {
        FormContent(
            contact = Contact()
        )
    }
}