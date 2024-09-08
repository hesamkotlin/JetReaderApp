package com.example.jetreaderapp.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetreaderapp.R
import com.example.jetreaderapp.components.EmailInput
import com.example.jetreaderapp.components.PasswordInput
import com.example.jetreaderapp.components.ReaderLogo

@Composable
fun ReaderLoginScreen(navController: NavController) {

    val showLoginForm = rememberSaveable { mutableStateOf(true) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ReaderLogo()
            if (showLoginForm.value) UserForm(
                loading = false,
                isCreateAccount = false
            ) { email, password ->
                //todo fb login
            }
            else UserForm(loading = false, isCreateAccount = true) { email, password ->
                //todo fb registration
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                val text = if (showLoginForm.value) "Sign up" else "Login"
                Text(text = "New User?")
                Text(text = text, modifier = Modifier
                    .clickable {
                        showLoginForm.value = !showLoginForm.value

                    }
                    .padding(5.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color.Green)

            }
        }
    }
}

@Preview
@Suppress
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = { email, password ->
        Log.d("user", "UserForm: $email, $password ")
    }
) {
    val email = rememberSaveable {
        mutableStateOf("")
    }
    val passWord = rememberSaveable {
        mutableStateOf("")
    }
    val passwordVisibility = rememberSaveable {
        mutableStateOf(false)
    }
    val passWordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, passWord.value) {
        email.value.trim().isNotEmpty() && passWord.value.trim().isNotEmpty()
    }
    val modifier = Modifier
        .height(250.dp)
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(rememberScrollState())

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if (isCreateAccount) Text(
            text = stringResource(id = R.string.create_acct),
            modifier = Modifier.padding(4.dp)
        ) else Text(text = "")
        EmailInput(
            emailState = email,
            enabled = !loading,
            onAction = KeyboardActions { passWordFocusRequest.requestFocus() })

        PasswordInput(
            modifier = Modifier.focusRequester(passWordFocusRequest),
            passWord = passWord,
            labelID = "Password",
            enabled = !loading, //todo: change sth
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(), passWord.value.trim())
                keyboardController?.hide()
            }
        )

        SubmitButton(
            textId = if (isCreateAccount) "Create Account" else "Login",
            loading = loading,
            validInputs = valid,
        ) {
            onDone(email.value.trim(), passWord.value.trim())
            keyboardController?.hide()
        }
    }
}

@Composable
fun SubmitButton(
    textId: String,
    loading: Boolean,
    validInputs: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxSize(),
        enabled = !loading && validInputs,
        shape = CircleShape
    ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = textId, modifier = Modifier.padding(5.dp))
    }
}




