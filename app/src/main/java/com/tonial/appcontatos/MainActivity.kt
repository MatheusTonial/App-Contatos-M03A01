package com.tonial.appcontatos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tonial.appcontatos.ui.contact.list.ContactsListScreen
import com.tonial.appcontatos.ui.theme.AppContatosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppContatosTheme {
                ContactsListScreen()
            }
        }
    }
}
