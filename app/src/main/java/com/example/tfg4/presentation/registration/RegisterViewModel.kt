package com.example.tfg4.presentation.registration

import android.content.Intent
import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.tfg4.Database.Controller
import com.example.tfg4.R
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.auth.FirebaseAuth
import dev.leonardom.loginjetpackcompose.navigation.Destinations
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {

    val state: MutableState<RegisterState> = mutableStateOf(RegisterState())

   val controller = Controller()

    fun register(
        email: String,
        password: String,
        confirmPassword: String
    ) {
        val errorMessage = if(email.isBlank() ||  password.isBlank() || confirmPassword.isBlank()){
            R.string.error_input_empty
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            R.string.error_not_a_valid_email
        } else if(password != confirmPassword) {
            R.string.error_incorrectly_repeated_password
        } else null

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
        controller.crearUser(email,password)

        errorMessage?.let {
            state.value = state.value.copy(errorMessage = errorMessage)
            return
        }

        viewModelScope.launch {
            state.value = state.value.copy(displayProgressBar = true)

            delay(3000)

            state.value = state.value.copy(displayProgressBar = false)
        }
    }

    fun hideErrorDialog() {
        state.value = state.value.copy(
            errorMessage = null
        )
    }

}