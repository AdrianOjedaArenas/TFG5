package dev.leonardom.loginjetpackcompose.navigation

import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument

sealed class Destinations(
    val route: String,
    val arguments: List<NamedNavArgument>
){

    object Login: Destinations("login", emptyList())
    object Register: Destinations("register", emptyList())
    object Home: Destinations("home", emptyList())
    object CreateEvent :Destinations("createEvent", emptyList())

}
