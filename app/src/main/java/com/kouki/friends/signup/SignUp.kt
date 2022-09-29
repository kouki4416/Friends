package com.kouki.friends.signup

import androidx.annotation.StringRes
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kouki.friends.R
import com.kouki.friends.domain.user.InMemoryUserCatalog
import com.kouki.friends.domain.user.UserRepository
import com.kouki.friends.domain.validation.CredentialsValidationResult
import com.kouki.friends.domain.validation.RegexCredentialValidator

@Composable
@Preview
fun SignUp(
    onSignedUp: () -> Unit
) {

    val credentialsValidator = RegexCredentialValidator()
    val userRepository = UserRepository(InMemoryUserCatalog())
    val signUpViewModel = SignUpViewModel(credentialsValidator, userRepository)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val signUpState by signUpViewModel.signUpState.observeAsState()

    if(signUpState is SignUpState.SignedUp){
        onSignedUp()
        signUpViewModel.createdAccount()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ScreenTitle(R.string.createAnAccount)
        Spacer(modifier = Modifier.height(16.dp))
        EmailField(
            email,
            onValueChanged = { email = it }
        )
        PasswordField(
            value = password,
            onValueChange = { password = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                signUpViewModel.createAccount(email, password, "")
            }
        ) {
            Text(text = stringResource(id = R.string.signUp))
        }
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
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        label = {
            Text(text = stringResource(id = R.string.email))
        },
        onValueChange = onValueChanged
    )
}

@Composable
private fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit
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
        trailingIcon = {
            VisibilityToggle(isVisible){
                isVisible = !isVisible
            }
        },
        visualTransformation = visualTransformation,
        label = {
            Text(text = stringResource(id = R.string.password))
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
        val resource = if(isVisible) R.drawable.ic_visible else R.drawable.ic_invisible
        Icon(
            painter = painterResource(id = resource),
            contentDescription = stringResource(id = R.string.toggleVisivility)
        )
    }
}

