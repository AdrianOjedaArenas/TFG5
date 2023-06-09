package com.example.tfg4.presentation.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tfg4.Database.Controller
import com.example.tfg4.Database.Eventos
import com.example.tfg4.R
import dev.leonardom.loginjetpackcompose.navigation.Destinations

@Composable
fun principal(
     mainViewModel:MainViewModel,
     navController: NavHostController
){
    val searchWidgetState by mainViewModel.searchWidgetState
    val searchTextState by mainViewModel.searchTextState
    Scaffold(
        //Barra de busqueda
        topBar = {
            MainAppBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = {
                    mainViewModel.updateSearchTextState(newValue = it)
                },
                onCloseClicked = {
                    mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                },
                onSearchClicked = {
                    Log.d("Searched Text", it)
                },
                onSearchTriggered = {
                    mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                }
            )
        },

       floatingActionButton = {bottomCreateEvent(modifier = Modifier, navController)},
        //Barra de botones de navegacion
        bottomBar = { BottomNavigation() }

    ) { padding ->

        EventList()


    }
}

@Composable
fun MessageCard(evento :Eventos) {

    // Add padding around our message
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
           painter = painterResource(R.drawable.logo_grouping),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size to 40 dp
                .size(40.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
        )

        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Row() {
                evento.titulo?.let { Text(text = it) }

                Text(
                    text = evento.fecha.toString(),
                    modifier = Modifier
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            evento.descripcion?.let { Text(text = it) }
        }
    }
}
//Lista de eventos
@Composable
fun EventList() {
    val c = Controller()
    val eventosListState = remember { mutableStateOf(emptyList<Eventos>()) }

    LaunchedEffect(Unit) {
        c.getAllEventos { eventosList ->
            eventosListState.value = eventosList
        }
    }

    LazyColumn {
        items(eventosListState.value) { evento ->
            MessageCard(evento)
        }
    }
}

@Composable
private fun bottomCreateEvent(modifier: Modifier = Modifier,navController:NavHostController) {

    FloatingActionButton(
        modifier = modifier
            .size(50.dp)
            ,
        backgroundColor = colors.primary,

        onClick = {
            //navegacion pantalla formulario para crear evento
            navController.navigate(Destinations.CreateEvent.route)

        }
    ) {
        Icon(
            modifier = Modifier.size(42.dp),
            imageVector = Icons.Default.Build,//Hay que poner icono de "+"
            contentDescription = "Forward Icon",
            tint = Color.White
        )
    }
}





@Composable
private fun BottomNavigation(modifier: Modifier = Modifier) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        modifier = modifier
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text("HOME")
            },
            selected = true,
            onClick = {
                //Falta meter navegacion


            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            label = {
                Text("PROFILE")
            },
            selected = false,
            onClick = {
                //Falta meter navegacion




            }
        )
    }
}