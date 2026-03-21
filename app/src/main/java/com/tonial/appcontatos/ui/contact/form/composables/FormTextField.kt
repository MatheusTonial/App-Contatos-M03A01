package com.tonial.appcontatos.ui.contact.form.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.tonial.appcontatos.ui.theme.AppContatosTheme

@Composable
fun FormTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    errorMessage: String = "",
    keyboardCaptalization: KeyboardCapitalization = KeyboardCapitalization.Unspecified,
    keyboardImeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable () -> Unit
) {
    val hasError = errorMessage.isNotBlank()
    Column(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            maxLines = enabled,
            enabled = enabled,
            readOnly = readOnly,
            isError = hasError,
            keyboardOptions = KeyboardOptions(
                capitalization = keyboardCaptalization,
                imeAction = keyboardImeAction,
                keyboardType = keyboardType
            ),
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon
        ){

        }
    }
}


@Composable
@Preview(showBackground = true)
fun FormTextFieldPreview() {
    AppContatosTheme {
        FormTextField()
    }
}