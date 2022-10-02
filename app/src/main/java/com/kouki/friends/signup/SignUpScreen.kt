package com.kouki.friends.signup

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kouki.friends.MainActivity
import com.kouki.friends.R

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel,
    navController: NavHostController
) {
    var email by remember { mutableStateOf("") }
    var isBadEmail by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var isBadPassword by remember { mutableStateOf(false) }
    var about by remember { mutableStateOf("") }
    val signUpState by signUpViewModel.signUpState.observeAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ScreenTitle(R.string.createAnAccount)
            Spacer(modifier = Modifier.height(16.dp))
            EmailField(
                value = email,
                isError = isBadEmail,
                onValueChanged = { email = it }
            )
            PasswordField(
                value = password,
                isError = isBadPassword,
                onValueChange = { password = it },
            )
            Spacer(modifier = Modifier.height(16.dp))
            AboutField(
                value = about,
                onValueChange = { about = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    signUpViewModel.createAccount(email, password, about)
                    if (signUpState is SignUpState.SignedUp) {
                        navController.navigate(MainActivity.TIMELINE)
                    }
                }
            ) {
                Text(text = stringResource(id = R.string.signUp))
            }
        }
        if (signUpState is SignUpState.BadEmail) {
            isBadEmail = true
        } else if (signUpState is SignUpState.BadPassword) {
            isBadPassword = true
        } else if (signUpState is SignUpState.DuplicateAccount) {
            InfoMessage(R.string.duplicateAccountError)
        } else if (signUpState is SignUpState.BackendError) {
            InfoMessage(stringResource = R.string.createAccountError)
        } else if (signUpState is SignUpState.Offline) {
            InfoMessage(stringResource = R.string.offlineError)
        }
    }

}

@Composable
fun InfoMessage(@StringRes stringResource: Int) {
    Surface(
        modifier = Modifier
            .background(MaterialTheme.colors.error)
            .fillMaxWidth(),
    ) {
        Text(text = stringResource(id = stringResource))
    }
}


@Composable
private fun ScreenTitle(@StringRes resource: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(resource),
            style = TextStyle(fontSize = integerResource(id = R.integer.h4).sp)
        )
    }
}

@Composable
private fun EmailField(
    value: String,
    isError: Boolean,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        isError = isError,
        label = {
            val resource = if (isError) R.string.badEmailError else R.string.email
            Text(text = stringResource(id = resource))
        },
        onValueChange = onValueChanged
    )
}

@Composable
private fun PasswordField(
    value: String,
    isError: Boolean,
    onValueChange: (String) -> Unit,
) {
    var isVisible by remember { mutableStateOf(false) }
    val visualTransformation = if (isVisible) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        isError = isError,
        trailingIcon = {
            VisibilityToggle(isVisible) {
                isVisible = !isVisible
            }
        },
        visualTransformation = visualTransformation,
        label = {
            val resource = if (isError) R.string.badPasswordError else R.string.password
            Text(text = stringResource(id = resource))
        },
        onValueChange = onValueChange
    )
}

@Composable
private fun VisibilityToggle(
    isVisible: Boolean,
    onToggle: () -> Unit
) {
    IconButton(
        onClick = onToggle
    ) {
        val resource = if (isVisible) R.drawable.ic_visible else R.drawable.ic_invisible
        Icon(
            painter = painterResource(id = resource),
            contentDescription = stringResource(id = R.string.toggleVisivility)
        )
    }
}

@Composable
fun AboutField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        label = {
            Text(text = stringResource(id = R.string.about))
        },
        onValueChange = onValueChange
    )
}