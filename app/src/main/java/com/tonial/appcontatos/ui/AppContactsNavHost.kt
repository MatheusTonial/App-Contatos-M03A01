package com.tonial.appcontatos.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tonial.appcontatos.ui.contact.form.ContactFormScreen
import com.tonial.appcontatos.ui.contact.list.ContactsListScreen

@Composable
fun AppContactsNavHost(
    modifier: Modifier = Modifier,
    naveController: NavHostController = rememberNavController(),
    startDestination: String = "list"
) {

    NavHost(
        modifier = modifier,
        navController = naveController,
        startDestination = startDestination
    ) {
        composable(route = "list") {
            ContactsListScreen(
                onAddPressed = {
                    naveController.navigate("form")
                },
                onContactPressed = {
                    naveController.navigate("form?id=${it.id}")
                }
            )
        }
        composable(
            route = "form?id={id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            ContactFormScreen(
                onBackPress = {
                    naveController.popBackStack()
                }
            )
        }
    }
}